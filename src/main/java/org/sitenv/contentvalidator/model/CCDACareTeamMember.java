package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDACareTeamMember {
	
	private static Logger log = LoggerFactory.getLogger(CCDACareTeamMember.class.getName());
	
	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDACareTeamMemberAct> memberActs;
	
	// Not used for USCDI - Dragon
	private ArrayList<CCDAParticipant> members;
	
	private CCDAAuthor	author;
	
	public void log() {
		
		for (int i = 0; i < members.size(); i++) {
			members.get(i).log();
		}
		
		if(author != null)
			author.log();
		
		for(int j = 0; j < memberActs.size(); j++) {
			memberActs.get(j).log();
		}
	}
	
	public CCDACareTeamMember()
	{
		members = new ArrayList<CCDAParticipant>();
		memberActs = new ArrayList<CCDACareTeamMemberAct>();
	}

	public ArrayList<CCDAParticipant> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<CCDAParticipant> ms) {
		
		if(ms != null)
			this.members = ms;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> sectionTemplateId) {
		this.sectionTemplateId = sectionTemplateId;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDACareTeamMemberAct> getMemberActs() {
		return memberActs;
	}

	public void setMemberActs(ArrayList<CCDACareTeamMemberAct> memberActs) {
		this.memberActs = memberActs;
	}
	
	public HashMap<String, CCDACareTeamMemberAct> getAllCareTeamMembers() {
		
		HashMap<String, CCDACareTeamMemberAct> members = new HashMap<String, CCDACareTeamMemberAct>();
		if(memberActs != null) {
			
			for(CCDACareTeamMemberAct ctm : memberActs) {
				
				if(ctm.getPrimaryPerformer() != null) {
					
					log.info(" Found Primary Performer in Care Team Member Act ");
					
					CCDAParticipant ppf = ctm.getPrimaryPerformer();
					
					String name = "";
					if(ppf.getFirstName() != null)
						name += ppf.getFirstName().getValue();
					
					if(ppf.getLastName() != null)
						name += " " + ppf.getLastName().getValue();
					
					if(!members.containsKey(name))
						members.put(name, ctm);
					   
				}
				
			}
		}
		
		return members;
		
	}
	
}
