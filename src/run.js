import {ROLE_ANONYMOUS, ROLE_USER} from "constants";

function defaultRun (Permission, authorisation) {
  "ngInject";
  Permission
    .defineRole(ROLE_ANONYMOUS, () => !authorisation.isAuthorised())
    .defineRole(ROLE_USER, () => authorisation.isAuthorised());
}

export default defaultRun;
