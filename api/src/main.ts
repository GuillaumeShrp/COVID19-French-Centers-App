import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import dataSample from './sites-prelevements-grand-public.json';
import { CovidTestCenter } from './CovidTestCenter';

async function bootstrap() {

  const app = await NestFactory.create(AppModule);
  await app.listen(3000);

}
bootstrap();
