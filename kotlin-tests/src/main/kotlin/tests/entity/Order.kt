package tests.entity

import jakarta.persistence.*

@Entity
@Table(name = "`order`")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var orderNumber: String? = null
)
