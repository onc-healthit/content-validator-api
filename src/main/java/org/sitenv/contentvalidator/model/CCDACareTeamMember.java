package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;

public class CCDACareTeamMember {
	
	private static Logger log = Logger.getLogger(CCDACareTeamMember.class.getName());

	private ArrayList<CCDAParticipant> members;
	
	private CCDAAuthor	author;
	
	public void log() {
		
		for (int i = 0; i < members.size(); i++) {
			members.get(i).log();
		}
		
		if(author != null)
			author.log();
	}
	
	public CCDACareTeamMember()
	{
		members = new ArrayList<CCDAParticipant>();
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
	
	
}
