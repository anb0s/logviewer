/*
 * Copyright 2009 by Andre Bossert
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package de.anbos.eclipse.logviewer.plugin.preferences.rule;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import de.anbos.eclipse.logviewer.plugin.LogViewerPlugin;
import de.anbos.eclipse.logviewer.plugin.preferences.Base64;


public class RulePreferenceData {
    
    // Attribute --------------------------------------------------------------------
	
	// Status
	protected int priority;
    private int position;
    private boolean enabled;
    
    // Rule
    private String ruleName;
    private String ruleValue;
    private String matchMode;
    private boolean caseInsensitive;

    // Action: coloring
    private boolean coloringEnabled;
	private RGB backgroundColor;
	private RGB foregroundColor;
    
    // Constructor ------------------------------------------------------------------
    
    public RulePreferenceData() {
    }
    
    // Public -----------------------------------------------------------------------
    
	public int getPriority() {
		return priority;
	}

	public int getPosition() {
		return position;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getRuleName() {
		return ruleName;
	}

	public String getRuleNameShort() {
		return LogViewerPlugin.getResourceString(ruleName);
	}
	
	public String getRuleValue() {
		return ruleValue;
	}

	public String getMatchMode() {
		return matchMode;
	}

	public boolean isCaseInsensitive() {
		return caseInsensitive;
	}

	public boolean isColoringEnabled() {
		return coloringEnabled;
	}

	public RGB getBackgroundColor() {
		return backgroundColor;
	}

	public RGB getForegroundColor() {
		return foregroundColor;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setRuleNameShort(String ruleName) {
		this.ruleName = LogViewerPlugin.getResourceString(ruleName);
	}
	
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public void setMatchMode(String matchMode) {
		if (matchMode.toLowerCase().indexOf("find") != -1)
			matchMode = "find";
		else 
			matchMode = "match";
		if (getRuleNameShort().toLowerCase().indexOf("word") != -1)
			matchMode = "find";
		this.matchMode = matchMode;
	}

	public void setCaseInsensitive(boolean caseInsensitive) {
		this.caseInsensitive = caseInsensitive;
	}

	public void setColoringEnabled(boolean coloringEnabled) {
		this.coloringEnabled = coloringEnabled;
	}

	public void setBackgroundColor(RGB backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setForegroundColor(RGB foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public boolean equals(Object object) {
    	if(!(object instanceof RulePreferenceData)) {
    		return false;
    	}
    	RulePreferenceData data = (RulePreferenceData)object;
    	if(		data.getPosition() == this.getPosition() &
    			data.getBackgroundColor().equals(this.getBackgroundColor()) &
    			data.getForegroundColor().equals(this.getForegroundColor()) &
    			data.getRuleName().equals(this.getRuleName()) &
    			data.getRuleValue().equals(this.getRuleValue()) &
    			data.isCaseInsensitive() == this.isCaseInsensitive() &
    			data.getMatchMode().equals(this.getMatchMode())) {
    		return true;
    	}
    	return false;
    }

	public boolean fillTokens(String value, String delimiter) {
		if(value == null || value.length() <= 0) {
			return false;
		}
		StringTokenizer tokenizer = new StringTokenizer(value,delimiter);
        String priorityStr = Base64.decode(tokenizer.nextToken());
        String enabledStr = Base64.decode(tokenizer.nextToken());
        String ruleNameStr = Base64.decode(tokenizer.nextToken());
        String backgroundStr = Base64.decode(tokenizer.nextToken());
        String foregroundStr = Base64.decode(tokenizer.nextToken());
		String ruleValueStr = Base64.decode(tokenizer.nextToken());
		String matchModeStr = null;
		try {
			matchModeStr = tokenizer.nextToken();
			matchModeStr = Base64.decode(matchModeStr);
		} catch (NoSuchElementException e) {
			matchModeStr = "match";
		}
		String caseInsensitiveStr = null;
		try {
			caseInsensitiveStr = tokenizer.nextToken();
			caseInsensitiveStr = Base64.decode(caseInsensitiveStr);
		} catch (NoSuchElementException e) {
			caseInsensitiveStr = "false";
		}
		// set members
		setPriority(Integer.parseInt(priorityStr));
		setEnabled(Boolean.valueOf(enabledStr).booleanValue());
		setRuleName(ruleNameStr);
		setBackgroundColor(StringConverter.asRGB(backgroundStr));
		setForegroundColor(StringConverter.asRGB(foregroundStr));
		setRuleValue(ruleValueStr);
		setMatchMode(matchModeStr);
		setCaseInsensitive(Boolean.valueOf(caseInsensitiveStr).booleanValue());
		return true;
	}
	
    // Private ----------------------------------------------------------------------
}
