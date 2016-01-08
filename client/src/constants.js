export const USER_REDIRECT_TO = 'msl.login';
export const ANONYMOUS_REDIRECT_TO = 'msl.home';
export const ROLE_USER = 'user';
export const ROLE_ANONYMOUS = 'anonymous';
export const GENRE_FACET_ID = 'A1';
export const RATING_FACET_ID = 'A2';
export const PAGE_SIZE = 12;

/*RESOURCES*/
export const LOGIN_EDGE = `:${process.env.LOGIN_PORT}/msl/v1/loginedge/`;
export const ACCOUNT_EDGE = `:${process.env.ACCOUNT_PORT}/msl/v1/accountedge/`;
export const CATALOG_EDGE = `:${process.env.CATALOG_PORT}/msl/v1/catalogedge/`;
export const RATINGS_EDGE = `:${process.env.RATINGS_PORT}/msl/v1/ratingsedge/`;
