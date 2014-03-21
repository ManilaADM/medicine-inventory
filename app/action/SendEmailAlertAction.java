package action;

import models.Medicine;
import play.Play;
import play.i18n.Messages;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public class SendEmailAlertAction 
{
	public void sendEmail(Medicine medicine) 
	{
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		if (medicine.isQuantifiable() && medicine.getCount() <= medicine.getNotificationAlertCount()) 
		{
			if (medicine.getCount() == 0) 
			{
				mail.setSubject(medicine.getBrandName() +" "+ Messages.get("email.medicine.subjet.zero"));
			} 
			else 
			{
				mail.setSubject(medicine.getBrandName() +" "+ Messages.get("email.medicine.subjet.limit", medicine.getCount()));
			}
			mail.addRecipient(Play.application().configuration().getString("email.to"));
			mail.addFrom(Play.application().configuration().getString("email.from"));
			String body = views.html.emailAlert.render(medicine).body();
			mail.sendHtml(body);
		}

	}
}
