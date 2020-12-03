import { Controller, Get } from '@nestjs/common';
import { Param } from '@nestjs/common';
import { AppService } from './app.service';
import { CovidTestCenter } from './CovidTestCenter';

@Controller('/covidCenters')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  async getCovidTestCenters(): Promise<CovidTestCenter[]> {
    return this.appService.getCovidTestCenters();
  }

  @Get("/search/:id")
  async getCovidTestCenter(@Param('id') id: string): Promise<CovidTestCenter> {
    return this.appService.getCovidTestCenter(id);
  }

  @Get("/rs/:rs")
  async getCovidTestCenterByRs(@Param('rs') rs: string): Promise<CovidTestCenter> {
    return this.appService.getCovidTestCenterByRs(rs);
  }

}
