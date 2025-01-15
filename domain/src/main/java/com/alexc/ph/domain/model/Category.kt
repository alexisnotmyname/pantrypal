package com.alexc.ph.domain.model

data class Category(
    val id: Long,
    val name: String,
    val deletable: Boolean
)

enum class DefaultCategories(val value: String) {
    GROCERY("Grocery"),
    MEAT_FISH("Meat and Fish"),
    EGGS_DAIRY("Eggs and dairy products"),
    DRINKS("Drinks"),
    CEREALS_PASTA("Cereals and pasta"),
    FROZEN_FOOD("Frozen food"),
    CANNED_FOOD("Canned food"),
    CONDIMENTS("Condiments"),
    BREAD_BAKERY("Bread and confectionery");

    companion object {
        private val map = entries.associateBy(DefaultCategories::value)
        fun fromString(type: String) = map[type]
    }

    override fun toString() = this.value
}