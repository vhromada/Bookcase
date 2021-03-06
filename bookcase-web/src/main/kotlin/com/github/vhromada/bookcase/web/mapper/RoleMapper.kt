package com.github.vhromada.bookcase.web.mapper

import com.github.vhromada.bookcase.web.fo.RoleFO
import com.github.vhromada.common.account.entity.UpdateRoles

/**
 * An interface represents mapper for roles.
 *
 * @author Vladimir Hromada
 */
interface RoleMapper {

    /**
     * Returns updating roles.
     *
     * @param source FO for role
     * @return updating roles
     */
    fun map(source: RoleFO): UpdateRoles

}
