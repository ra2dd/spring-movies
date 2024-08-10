import { PassedInitialConfig } from 'angular-auth-oidc-client';
import { environment } from '../../environments/environment';

export const authConfig: PassedInitialConfig = {
  config: {
            authority: environment.authAuthority,
            redirectUrl: `${window.location.origin}/redirect`,
            postLoginRoute: '/callback',
            postLogoutRedirectUri: window.location.origin,
            clientId: environment.authClientId,
            scope: 'openid profile offline_access',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            secureRoutes: [environment.interceptorSecureRoute],
            customParamsAuthRequest: {
              audience: environment.interceptorAudience,
            }
        }
}