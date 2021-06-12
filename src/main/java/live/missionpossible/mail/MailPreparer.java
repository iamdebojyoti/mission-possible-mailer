package live.missionpossible.mail;

import live.missionpossible.MailContent;
import live.missionpossible.models.User;

public interface MailPreparer {

	MailContent prepareMail(User user);
}
