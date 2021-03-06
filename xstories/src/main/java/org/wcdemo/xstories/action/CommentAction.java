package org.wcdemo.xstories.action;

import org.wcdemo.xstories.Comment;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import org.witchcraft.seam.action.BaseAction;

@Scope(ScopeType.CONVERSATION)
@Name("commentAction")
public class CommentAction extends BaseAction<Comment>
		implements
			java.io.Serializable {

	@In(create = true)
	@Out(required = false)
	@DataModelSelection
	private Comment comment;

	@DataModel
	private List<Comment> commentList;

	@Factory("commentList")
	public void findRecords() {
		commentList = entityManager.createQuery(
				"select comment from Comment comment order by comment.id desc")
				.getResultList();
	}

	public Comment getEntity() {
		return comment;
	}

	@Override
	public void setEntity(Comment t) {
		this.comment = t;
	}

	@Override
	public void setEntityList(List<Comment> list) {
		this.commentList = list;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	public void addAssoications(Criteria criteria) {

		if (comment.getStory() != null) {
			criteria = criteria.add(Restrictions.eq("story.id", comment
					.getStory().getId()));
		}

	}

}
