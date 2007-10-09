package com.googlecode.jsu.workflow.validator;

import java.util.Collection;

import com.atlassian.jira.issue.fields.Field;
import com.googlecode.jsu.annotation.Argument;
import com.googlecode.jsu.util.CommonPluginUtils;
import com.googlecode.jsu.util.WorkflowUtils;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.WorkflowException;

/**
 * This validator verifies that certain fields must be required at execution of a transition.
 * 
 * @author Gustavo Martin
 */
public class FieldsRequiredValidator extends GenericValidator {
	@Argument("hidFieldsList")
	private String fieldList;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.workflow.Validator#validate(java.util.Map, java.util.Map, com.opensymphony.module.propertyset.PropertySet)
	 */
	protected void validate() throws InvalidInputException, WorkflowException {
		// It obtains the fields that are required for the transition.
		Collection<Field> fieldsSelected = WorkflowUtils.getFields(fieldList, WorkflowUtils.SPLITTER);
		
		for (Field field : fieldsSelected) {
			Object fieldValue = WorkflowUtils.getFieldValueFromIssue(getIssue(), field);
			
			if ((fieldValue == null) && !CommonPluginUtils.isFieldHidden(getIssue(), field)) {
				// Sets Exception message.
				this.setExceptionMessage(
						field, 
						field.getName() + " is required.", 
						field.getName() + " is required. But it is not present on screen."
				);
			}
		}
	}
}