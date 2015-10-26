import {ROLE_ANONYMOUS, ROLE_USER} from 'constants';

export default function defaultRun(Permission, authorisation) {
  'ngInject';
  Permission
    .defineRole(ROLE_ANONYMOUS, () => !authorisation.isAuthorised())
    .defineRole(ROLE_USER, () => authorisation.isAuthorised());
}
