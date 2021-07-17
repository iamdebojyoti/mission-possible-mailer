package live.missionpossible;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.InputStreamSource;

public class MailContent {

	private String subject;

	private String to;
	
	private String cc;

	private String body;

	private Pair<String, InputStreamSource> attachment;

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the attachment
	 */
	public Pair<String, InputStreamSource> getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Pair<String, InputStreamSource> attachment) {
		this.attachment = attachment;
	}
	
}
