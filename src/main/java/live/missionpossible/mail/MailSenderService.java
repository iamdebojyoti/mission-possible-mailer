package live.missionpossible.mail;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import live.missionpossible.MailContent;
import live.missionpossible.models.User;
import live.missionpossible.parser.DonationFileParser;
import live.missionpossible.parser.RegistrationFileParser;
import live.missionpossible.types.AppMode;

@Service
public class MailSenderService {

	@Autowired
	private RegistrationFileParser registrationFileParser;
	
	@Autowired
	private DonationFileParser donationFileParser;
	
	@Autowired
	private RegistrationMailPreparer registrationMailPreparer;
	
	@Autowired
	private DonationMailPreparer donationMailPreparer;
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void send(String fileName, String sheetName, AppMode mode) throws Exception {
		File file = new File(fileName);
		
		if (mode == AppMode.REGISTRATION) {
			List<User> users = registrationFileParser.parse(file, sheetName);
			List<MailContent> mailContents = users.stream()
					.map(user -> StringUtils.isNotEmpty(user.getEventName()) ? registrationMailPreparer.prepareMail(user)
							: donationMailPreparer.prepareMail(user))
					.collect(Collectors.toList());
			sendMail(mailContents);
		} else {
			List<User> users = donationFileParser.parse(file, sheetName);
			List<MailContent> mailContents = users.stream()
									.map(user -> donationMailPreparer.prepareMail(user))
									.collect(Collectors.toList());
			sendMail(mailContents);
		}
	}
	
	private void sendMail(List<MailContent> mailContents) {
		mailContents.forEach(mailContent -> {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setSubject(mailContent.getSubject());
				helper.setText(mailContent.getBody(),true);
				helper.setTo(mailContent.getTo());

				mailSender.send(mimeMessage);
				System.out.println("Mail Sent successfully to " + mailContent.getTo());
			} catch (MessagingException | MailException e) {
				System.out.println("[ERROR] Failed to sent message to " + mailContent.getTo());
				e.printStackTrace();
			}
		});
	}
}
