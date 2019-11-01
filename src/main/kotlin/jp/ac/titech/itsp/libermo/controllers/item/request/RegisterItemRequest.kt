package jp.ac.titech.itsp.libermo.controllers.item.request

data class RegisterItemRequest(
    val name: String,
    val author: String,
    val description: String
)