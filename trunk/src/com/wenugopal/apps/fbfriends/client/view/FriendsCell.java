package com.wenugopal.apps.fbfriends.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;

public class FriendsCell  extends AbstractCell<FQLFriendsDetails> {

	@Override
	public void render(Context context, FQLFriendsDetails value, SafeHtmlBuilder sb) {

		// Always do a null check on the value. Cell widgets can pass null to cells
	      // if the underlying data contains a null, or if the data arrives out of order.
	      if (value == null) {
	        return;
	      }

	      // If the value comes from the user, we escape it to avoid XSS attacks.
	      // Append some HTML that sets the text color.
//	      sb.appendHtmlConstant("<div>");
//	      sb.appendEscaped(value.getName());
//	      sb.appendHtmlConstant("</div>");
	      
	      sb.appendHtmlConstant("<table width='100%' style='text-align: left;'>");

	      // Add the contact image.
	      sb.appendHtmlConstant("<tr style='font-size:95%; text-align: left;'><td rowspan='3' style='text-align: left;'>");
	      sb.appendHtmlConstant("<img src='"+value.getPic_square()+"'/>");
	      sb.appendHtmlConstant("</td>");

	      // Add the name and address.
	      sb.appendHtmlConstant("<td style='font-size:95%; text-align: left;'>");
	      sb.appendEscaped(value.getName());
	      sb.appendHtmlConstant("</td></tr><tr style='text-align: left;'><td style='font-size:95%; text-align: left;'>");
	      sb.appendEscaped(value.getUid());
	      sb.appendHtmlConstant("</td></tr></table>");
	}

}
