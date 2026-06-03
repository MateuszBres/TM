
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
import { errorInterceptor } from './core/error.interceptor';



export const appConfig: ApplicationConfig = {

  providers: [

    provideZoneChangeDetection({
      eventCoalescing: true
    }),

    provideRouter(routes),

    provideHttpClient(

      withInterceptors([

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