import { ROLE_ANONYMOUS, ROLE_USER } from 'constants';

export default function defaultRun(Permission, authentication) {
  'ngInject';
  Permission
    .defineRole(ROLE_ANONYMOUS, () => !authentication.isAuthenticated())
    .defineRole(ROLE_USER, () => authentication.isAuthenticated());
}
