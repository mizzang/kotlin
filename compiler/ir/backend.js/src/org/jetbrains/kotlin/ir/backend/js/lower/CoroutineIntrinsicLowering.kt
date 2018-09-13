/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.backend.js.lower

import org.jetbrains.kotlin.backend.common.FileLoweringPass
import org.jetbrains.kotlin.backend.common.isBuiltInIntercepted
import org.jetbrains.kotlin.backend.common.isBuiltInSuspendCoroutineUninterceptedOrReturn
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid

class CoroutineIntrinsicLowering(val context: JsIrBackendContext): FileLoweringPass {
    private val languageVersion = context.configuration.languageVersionSettings
    override fun lower(irFile: IrFile) {
        irFile.transformChildrenVoid(object : IrElementTransformerVoid() {
            override fun visitCall(expression: IrCall): IrExpression {
                val call = super.visitCall(expression)
                return when {
                    expression.descriptor.isBuiltInSuspendCoroutineUninterceptedOrReturn(languageVersion) ->
                        copyCall(expression, context.coroutineSuspendOrReturn.owner)
                    expression.descriptor.isBuiltInIntercepted(languageVersion) ->
                        error("Intercepted should not be used with release coroutines")
                    expression.symbol.owner == context.intrinsics.jsCoroutineContext.owner ->
                        copyCall(expression, context.coroutineGetContextJs.owner)
                    else -> call
                }
            }
        })
    }

    private fun copyCall(expression: IrCall, function: IrFunction) = expression.run {
        IrCallImpl(
            startOffset,
            endOffset,
            type,
            function.symbol,
            function.descriptor,
            typeArgumentsCount,
            origin,
            superQualifierSymbol
        )
    }.also {
        for (i in 0 until expression.valueArgumentsCount) {
            it.putValueArgument(i, expression.getValueArgument(i))
        }
        for (i in 0 until expression.typeArgumentsCount) {
            it.putTypeArgument(i, expression.getTypeArgument(i))
        }
    }
}
