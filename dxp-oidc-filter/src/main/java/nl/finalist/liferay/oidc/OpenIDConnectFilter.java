package nl.finalist.liferay.oidc;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import org.osgi.service.component.annotations.Component;

import nl.finalist.liferay.oidc.LibFilter.FilterResult;

@Component(
		immediate = true,
        property = {
                "dispatcher=FORWARD",
                "dispatcher=REQUEST",
                "servlet-context-name=",
                "servlet-filter-name=SSO OpenID Connect Filter",
                "url-pattern=/c/portal/login"
        },
        service = Filter.class
)
public class OpenIDConnectFilter extends BaseFilter {

    private static final Log LOG = LogFactoryUtil.getLog(OpenIDConnectFilter.class);
    private LibFilter libFilter;

    @Override
    protected Log getLog() {
        return LOG;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        super.init(filterConfig);
        libFilter = new LibFilter(new Liferay70());

    }

    @Override
    protected void processFilter(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws Exception {

        FilterResult filterResult = libFilter.processFilter(request, response, filterChain);
        if (filterResult == FilterResult.CONTINUE_CHAIN) {
        	processFilter(getClass().getName(), request, response, filterChain);
        	
        }
    }


}