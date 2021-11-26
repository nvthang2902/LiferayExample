package com.liferay.docs.guestbook.portlet.portlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;


import com.liferay.docs.guestbook.constants.GuestbookPortletKeys;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.hidden",
                "com.liferay.portlet.scopeable=true",
                "javax.portlet.display-name=Guestbooks",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.portlet-title-based-navigation=true",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/guestbook_admin/view.jsp",
                "javax.portlet.name=" + GuestbookPortletKeys.GUESTBOOK_ADMIN,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=administrator",
                "javax.portlet.supports.mime-type=text/html",
                "com.liferay.portlet.add-default-resource=true",
                "com.liferay.portlet.display-category=category.hidden"
        },
        service = Portlet.class
)
public class GuestbookAdminPortlet extends MVCPortlet {
        public void addGuestbook(ActionRequest request, ActionResponse response)
                throws PortalException {

                ServiceContext serviceContext = ServiceContextFactory.getInstance(
                        Guestbook.class.getName(), request);

                String name = ParamUtil.getString(request, "name");

                try {
                        _guestbookLocalService.addGuestbook(
                                serviceContext.getUserId(), name, serviceContext);
                        SessionMessages.add(request, "guestbookAdded");
                } catch (PortalException pe) {

                        Logger.getLogger(GuestbookAdminPortlet.class.getName()).log(
                                Level.SEVERE, null, pe);
                        SessionErrors.add(request, pe.getClass().getName());

                        response.setRenderParameter(
                                "mvcPath", "/guestbook_admin/edit_guestbook.jsp");
                }

        }
        @Reference
        private GuestbookLocalService _guestbookLocalService;
}
