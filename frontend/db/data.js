export let token = null;
export let user = null;
export let userRole = null;
export let alerts = [];

export const setUser = u => user = u;
export const setToken = t => token = t;
export const setUserRole = r => userRole = r;
export const addAlert = (a) => alerts.push(a);