package com.miumiuhaskeer.fastmessage.statistic.model.entity

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "fm_user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen")
    @SequenceGenerator(name = "id_gen", sequenceName = "fm_user_id_seq", allocationSize = 1)
    var id: Long? = null

    var email: String? = null
    var password: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "fm_user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Set<Role> = HashSet()

    @CreationTimestamp
    @Column(updatable = false)
    var creationDateTime: LocalDateTime? = null
}