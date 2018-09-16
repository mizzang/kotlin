/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package kotlin.contracts

import kotlin.internal.ContractsDsl
import kotlin.internal.InlineOnly

/**
 * The experimental contract declaration marker. This marker distinguishes the API that is used to declare
 * function contracts in their bodies.
 *
 * Any usage of a declaration annotated with `@ExperimentalContracts` must be accepted either by
 * annotating that usage with the [UseExperimental] annotation, e.g. `@UseExperimental(ExperimentalContracts::class)`,
 * or by using the compiler argument `-Xuse-experimental=kotlin.contracts.ExperimentalContracts`.
 */
@Retention(AnnotationRetention.BINARY)
@SinceKotlin("1.3")
@Experimental
public annotation class ExperimentalContracts

/**
 * Provides the functions of the contract DSL, such as [returns], [callsInPlace], etc.,
 * that are used to describe the contract of a function.
 *
 * This type is used as the receiver of the lambda function passed to the [contract] function.
 *
 * @see contract
 */
@ContractsDsl
@ExperimentalContracts
@SinceKotlin("1.3")
public interface ContractBuilder {
    /**
     * Starts an effect declaration that is caused by the function returning normally.
     *
     * Use [SimpleEffect.implies] function to describe the effect that happens in such case.
     *
     * @sample samples.contracts.returnsContract
     */
    @ContractsDsl public fun returns(): Returns

    /**
     * Starts an effect declaration that is caused by this function returning the specified value.
     *
     * The possible values of [value] are limited to `true`, `false` or `null`.
     *
     * Use [SimpleEffect.implies] function to describe the effect that happens in such case.
     *
     * @sample samples.contracts.returnsTrueContract
     * @sample samples.contracts.returnsFalseContract
     * @sample samples.contracts.returnsNullContract
     */
    @ContractsDsl public fun returns(value: Any?): Returns

    /**
     * Starts an effect declaration that is caused by this function returning any value that is not `null`.
     *
     * Use [SimpleEffect.implies] function to describe the effect that happens in such case.
     *
     * @sample samples.contracts.returnsNotNullContract
     */
    @ContractsDsl public fun returnsNotNull(): ReturnsNotNull

    /**
     * Declares an effect of calling the function for its [lambda] function parameter. The contract specifies how that
     * function is invoked and how many times.
     *
     * This contract specifies that:
     *  1) the [lambda] function can only be invoked during the call of the owner function,
     *     and it won't be invoked after that owner function is finished;
     *  2) the [lambda] function is invoked the amount of times specified by the [kind] parameter.
     *
     * Note that a function with the `callsInPlace` effect must be inline.
     *
     * @sample samples.contracts.callsInPlaceAtMostOnceContract
     * @sample samples.contracts.callsInPlaceAtLeastOnceContract
     * @sample samples.contracts.callsInPlaceExactlyOnceContract
     * @sample samples.contracts.callsInPlaceUnknownContract
     */
    @ContractsDsl public fun <R> callsInPlace(lambda: Function<R>, kind: InvocationKind = InvocationKind.UNKNOWN): CallsInPlace
}

/**
 * The amount of times the function parameter of a function with the [ContractBuilder.callsInPlace] contract
 * can be invoked.
 */
@ContractsDsl
@ExperimentalContracts
@SinceKotlin("1.3")
public enum class InvocationKind {
    /**
     * Expresses that the function parameter will be invoked zero or one time.
     *
     * @sample samples.contracts.callsInPlaceAtMostOnceContract
     */
    @ContractsDsl AT_MOST_ONCE,

    /**
     * Expresses that the function parameter will be invoked one or more times.
     *
     * @sample samples.contracts.callsInPlaceAtLeastOnceContract
     */
    @ContractsDsl AT_LEAST_ONCE,

    /**
     * Expresses that the function parameter will be invoked exactly one time.
     *
     * @sample samples.contracts.callsInPlaceExactlyOnceContract
     */
    @ContractsDsl EXACTLY_ONCE,

    /**
     * Expresses that the function parameter is called in place, but it's unknown how many times it can be called.
     *
     * @sample samples.contracts.callsInPlaceUnknownContract
     */
    @ContractsDsl UNKNOWN
}

/**
 * The function to describe a contract.
 *
 * The contract description must be at the beginning of a function and have at least one effect.
 * Also the contract description can be used only in the top-level functions.
 *
 * @param builder the lambda in the body of which the effects from the [ContractBuilder] are specified.
 *
 * @sample samples.contracts.returnsContract
 * @sample samples.contracts.returnsTrueContract
 * @sample samples.contracts.returnsFalseContract
 * @sample samples.contracts.returnsNullContract
 * @sample samples.contracts.returnsNotNullContract
 * @sample samples.contracts.callsInPlaceAtMostOnceContract
 * @sample samples.contracts.callsInPlaceAtLeastOnceContract
 * @sample samples.contracts.callsInPlaceExactlyOnceContract
 * @sample samples.contracts.callsInPlaceUnknownContract
 */
@ContractsDsl
@ExperimentalContracts
@InlineOnly
@SinceKotlin("1.3")
@Suppress("UNUSED_PARAMETER")
public inline fun contract(builder: ContractBuilder.() -> Unit) { }