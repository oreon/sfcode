package com.oreon.proj.web.action.questionnaire;

import com.oreon.proj.questionnaire.AnswersProvided;

import org.witchcraft.seam.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang.StringUtils;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.Component;
import org.jboss.seam.security.Identity;

import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.annotations.web.RequestParameter;

import org.witchcraft.base.entity.FileAttachment;

import org.apache.commons.io.FileUtils;

import org.primefaces.model.DualListModel;

import org.witchcraft.seam.action.BaseAction;
import org.witchcraft.base.entity.BaseEntity;

//
public abstract class AnswersProvidedActionBase
		extends
			BaseAction<AnswersProvided> implements java.io.Serializable {

	@RequestParameter
	protected Long answersProvidedId;

	public void setAnswersProvidedId(Long id) {
		setEntityId(id);
	}

	/** for modal dlg we need to load associaitons regardless of postback
	 * @param id
	 */
	public void setAnswersProvidedIdForModalDlg(Long id) {
		setEntityIdForModalDlg(id);
	}

	public Long getAnswersProvidedId() {
		return (Long) getId();
	}

	public AnswersProvided getAnswersProvided() {
		return getEntity();
	}

	@Override
	@Restrict("#{s:hasPermission('answersProvided', 'edit')}")
	public String save(boolean endconv) {
		return super.save(endconv);
	}

	@Override
	@Restrict("#{s:hasPermission('answersProvided', 'delete')}")
	public void archiveById() {
		super.archiveById();
	}

	@Override
	protected AnswersProvided createInstance() {
		AnswersProvided instance = super.createInstance();

		return instance;
	}

	/**
	 * Adds the contained associations that should be available for a newly created object e.g. 
	 * An order should always have at least one order item . Marked in uml with 1..* multiplicity
	 */
	private void addDefaultAssociations() {
		if (isNew()) {
			instance = getInstance();

		}
	}

	public void wire() {
		/*
		if (isNew()){
			getInstance();
				 
					com.oreon.proj.questionnaire.AnsweredQuestion answeredQuestion = answeredQuestionAction.getInstance();
					if (answeredQuestion != null  ) {
						 getInstance().setAnsweredQuestion(answeredQuestion);
					}
				 
				 
					com.oreon.proj.questionnaire.Answer answer = answerAction.getInstance();
					if (answer != null  ) {
						 getInstance().setAnswer(answer);
					}
				 
			
		}
		 */
	}

	public AnswersProvided getDefinedInstance() {
		return (AnswersProvided) (isIdDefined() ? getInstance() : null);
	}

	public void setAnswersProvided(AnswersProvided t) {
		setEntity(t);
	}

	@Override
	public Class<AnswersProvided> getEntityClass() {
		return AnswersProvided.class;
	}

	/** This function adds associated entities to an example criterion
	 * @see org.witchcraft.model.support.dao.BaseAction#createExampleCriteria(java.lang.Object)
	 */
	@Override
	public void addAssociations(Criteria criteria) {

		if (instance.getAnsweredQuestion() != null) {
			criteria = criteria.add(Restrictions.eq("answeredQuestion.id",
					instance.getAnsweredQuestion().getId()));
		}

		if (instance.getAnswer() != null) {
			criteria = criteria.add(Restrictions.eq("answer.id", instance
					.getAnswer().getId()));
		}

	}

	public String viewAnswersProvided() {
		load(currentEntityId);
		return "viewAnswersProvided";
	}

}
