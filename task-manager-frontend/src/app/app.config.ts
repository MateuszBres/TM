// import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
// import { provideRouter } from '@angular/router';
// import { provideHttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';

// import { routes } from './app.routes';
// import { authInterceptor } from './auth/auth.interceptor';
// import { errorInterceptor } from './auth/error.interceptor';

// export const appConfig: ApplicationConfig = {
//   providers: [
//     provideZoneChangeDetection({ eventCoalescing: true }),
//     provideRouter(routes),
//     provideHttpClient(),
    
//     {
//       provide: HTTP_INTERCEPTORS,
//       useValue: authInterceptor,
//       multi: true
//     },
//     {
//       provide: HTTP_INTERCEPTORS,
//       useValue: errorInterceptor,
//       multi: true
//     },
    

   


//   ]
  
// };
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';

import { provideRouter } from '@angular/router';

import {
  provideHttpClient,
  withInterceptors
} from '@angular/common/http';

import { provideNativeDateAdapter } from '@angular/material/core';

import {
  provideTranslateService
} from '@ngx-translate/core';

import {
  provideTranslateHttpLoader
} from '@ngx-translate/http-loader';

import { routes } from './app.routes';

import { authInterceptor } from './auth/auth.interceptor';

import { errorInterceptor } from './auth/error.interceptor';

export const appConfig: ApplicationConfig = {

  providers: [

    provideZoneChangeDetection({
      eventCoalescing: true
    }),

    provideRouter(routes),

    provideHttpClient(

      withInterceptors([
        authInterceptor,
        errorInterceptor
      ])

    ),

    provideNativeDateAdapter(),

    provideTranslateService({

      loader: provideTranslateHttpLoader({

        prefix: './i18n/',

    suffix: '.json'

      })

    })

  ]
};