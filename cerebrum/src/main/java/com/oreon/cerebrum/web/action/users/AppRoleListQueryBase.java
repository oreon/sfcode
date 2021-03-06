package com.oreon.cerebrum.web.action.users;

import com.oreon.cerebrum.users.AppRole;

import org.witchcraft.seam.action.BaseAction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.witchcraft.base.entity.BaseQuery;
import org.witchcraft.base.entity.Range;

import org.jboss.seam.annotations.Observer;

import java.math.BigDecimal;

import org.jboss.seam.annotations.security.Restrict;

import org.jboss.seam.annotations.In;

import com.oreon.cerebrum.users.AppRole;

/**
 * 
 * @author WitchcraftMDA Seam Cartridge - 
 *
 */
public abstract class AppRoleListQueryBase extends BaseQuery<AppRole, Long> {

	private static final String EJBQL = "select appRole from AppRole appRole";

	protected AppRole appRole = new AppRole();

	@In(create = true)
	AppRoleAction appRoleAction;

	public AppRoleListQueryBase() {
		super();
		setOrderColumn("id");
		setOrderDirection("desc");
	}

	public AppRole getAppRole() {
		return appRole;
	}

	@Override
	public AppRole getInstance() {
		return getAppRole();
	}

	private com.oreon.cerebrum.users.AppUser appUsersToSearch;

	public void setAppUsersToSearch(
			com.oreon.cerebrum.users.AppUser appUserToSearch) {
		this.appUsersToSearch = appUserToSearch;
	}

	public com.oreon.cerebrum.users.AppUser getAppUsersToSearch() {
		return appUsersToSearch;
	}

	@Override
	protected String getql() {
		return EJBQL;
	}

	@Override
	//@Restrict("#{s:hasPermission('appRole', 'view')}")
	public List<AppRole> getResultList() {
		return super.getResultList();
	}

	@Override
	public Class<AppRole> getEntityClass() {
		return AppRole.class;
	}

	@Override
	public String[] getEntityRestrictions() {
		return RESTRICTIONS;
	}

	private static final String[] RESTRICTIONS = {
			"appRole.id = #{appRoleList.appRole.id}",

			"appRole.archived = #{appRoleList.appRole.archived}",

			"lower(appRole.name) like concat(lower(#{appRoleList.appRole.name}),'%')",

			"#{appRoleList.appUsersToSearch} in elements(appRole.appUsers)",

			"appRole.dateCreated <= #{appRoleList.dateCreatedRange.end}",
			"appRole.dateCreated >= #{appRoleList.dateCreatedRange.begin}",};

	@Observer("archivedAppRole")
	public void onArchive() {
		refresh();
	}

	//@Restrict("#{s:hasPermission('appRole', 'delete')}")
	public void archiveById(Long id) {
		appRoleAction.archiveById(id);
		refresh();
	}

	/** create comma delimited row 
	 * @param builder
	 */
	//@Override
	public void createCsvString(StringBuilder builder, AppRole e) {

		builder.append("\""
				+ (e.getName() != null ? e.getName().replace(",", "") : "")
				+ "\",");

		builder.append("\"" + (e.getAppUsers() != null ? e.getAppUsers() : "")
				+ "\",");

		builder.append("\r\n");
	}

	/** create the headings 
	 * @param builder
	 */
	//@Override
	public void createCSvTitles(StringBuilder builder) {

		builder.append("Name" + ",");

		builder.append("AppUsers" + ",");

		builder.append("\r\n");
	}
}
