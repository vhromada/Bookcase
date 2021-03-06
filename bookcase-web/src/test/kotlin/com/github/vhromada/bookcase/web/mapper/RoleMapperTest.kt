package com.github.vhromada.bookcase.web.mapper

import com.github.vhromada.bookcase.web.BookcaseMapperTestConfiguration
import com.github.vhromada.bookcase.web.fo.RoleFO
import com.github.vhromada.common.account.entity.UpdateRoles
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [RoleFO] and [UpdateRoles].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseMapperTestConfiguration::class])
class RoleMapperTest {

    /**
     * Mapper for roles
     */
    @Autowired
    private lateinit var mapper: RoleMapper

    /**
     * Test method for [RoleMapper.map].
     */
    @Test
    fun map() {
        val role = RoleFO(roles = listOf("ROLE"))

        val updateRoles = mapper.map(role)

        assertThat(updateRoles).isNotNull
        assertThat(updateRoles.roles).isEqualTo(role.roles)
    }

}
