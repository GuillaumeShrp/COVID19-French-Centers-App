import { Controller, Get } from '@nestjs/common';
import { AppService } from './app.service';
import { CovidTestCenter } from './CovidTestCenter';

@Controller('/covidCenters')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  async getCovidTestCenters(): Promise<CovidTestCenter[]> {
    return this.appService.getCovidTestCenters();
  }
}
