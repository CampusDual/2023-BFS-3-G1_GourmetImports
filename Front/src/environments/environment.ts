// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  apiEndpoint: 'http://localhost:33333',
  production: false,
  versions: {
    core: '8.13.0',
    charts: '8.2.0',
    filemanager: '8.6.1',
    map: '8.0.1',
    theming: '8.5.2',
    report: '8.1.1'
  }
};
