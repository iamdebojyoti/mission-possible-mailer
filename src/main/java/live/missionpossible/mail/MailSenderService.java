package live.missionpossible.mail;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import live.missionpossible.MailContent;
import live.missionpossible.models.User;
import live.missionpossible.parser.DonationFileParser;
import live.missionpossible.parser.ParticipationFileParser;
import live.missionpossible.types.AppMode;

@Service
public class MailSenderService {
	
	private Logger logger = LoggerFactory.getLogger(MailSenderService.class);

	@Autowired
	private ParticipationFileParser participationFileParser;
	
	@Autowired
	private DonationFileParser donationFileParser;
	
	@Autowired
	private ParticipationRegnMailPreparer participationMailPreparer;
	
	@Autowired
	private DonationRegMailPreparer donationMailPreparer;
	
	@Autowired
	private CertificationMailPreparer certificationMailPreparer;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${application.cc.address}")
	private String ccAddress;
	
	public void send(String fileName, String sheetName, AppMode mode) throws Exception {
		File file = new File(fileName);
		
		if (mode == AppMode.PARTICIPATION) {
			List<User> users = participationFileParser.parse(file, sheetName);
			List<MailContent> mailContents = users.stream()
					.map(user -> StringUtils.isNotEmpty(user.getEventName())
							? participationMailPreparer.prepareMail(user)
							: donationMailPreparer.prepareMail(user))
					.collect(Collectors.toList());
			sendMail(mailContents);
		} else if (mode == AppMode.DONATION) {
			List<User> users = donationFileParser.parse(file, sheetName);
			List<MailContent> mailContents = users.stream().map(user -> donationMailPreparer.prepareMail(user))
					.collect(Collectors.toList());
			sendMail(mailContents);
		} else {
			List<User> users = participationFileParser.parse(file, sheetName);
			List<MailContent> mailContents = users.stream()
					.map(user -> certificationMailPreparer.prepareMail(user))
					.collect(Collectors.toList());
			sendMail(mailContents);
		}
	}
	
	private void sendMail(List<MailContent> mailContents) {
		mailContents.forEach(mailContent -> {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				
				helper.setSubject(mailContent.getSubject());
				helper.setText(mailContent.getBody(), true);
				helper.setTo(mailContent.getTo());
				
				if(StringUtils.isNotBlank(ccAddress)) {
					helper.setCc(mailContent.getCc());
				}
				
				if (mailContent.getAttachment() != null) {
					helper.addAttachment(mailContent.getAttachment().getLeft(), mailContent.getAttachment().getRight());
				}
				
				mailSender.send(mimeMessage);
				logger.info("Mail Sent successfully to " + mailContent.getTo());
				System.out.println("Mail Sent successfully to " + mailContent.getTo());
			} catch (MessagingException | MailException e) {
				logger.error("[ERROR] Failed to sent message to " + mailContent.getTo(), e);
				System.out.println("[ERROR] Failed to sent message to " + mailContent.getTo());
				e.printStackTrace();
			}
		});
	}
}
