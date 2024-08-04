import expressLoader from './express';
import dependencyInjectorLoader from './dependencyInjector';
import mongooseLoader from './mongoose';
import Logger from './logger';

import config from '../../config';

export default async ({ expressApp }) => {
  const mongoConnection = await mongooseLoader();
  Logger.info('✌️ DB loaded and connected!');

  const userSchema = {
    // compare with the approach followed in repos and services
    name: 'userSchema',
    schema: '../persistence/schemas/userSchema',
  };

  const percursoSchema = {
    name: 'percursoSchema',
    schema: '../persistence/schemas/percursoSchema',
  };

  const roleSchema = {
    // compare with the approach followed in repos and services
    name: 'roleSchema',
    schema: '../persistence/schemas/roleSchema',
  };

  const camiaoSchema = {
    // compare with the approach followed in repos and services
    name: 'camiaoSchema',
    schema: '../persistence/schemas/camiaoSchema',
  };
  const planeamentoSchema = {
    // compare with the approach followed in repos and services
    name: 'planeamentoSchema',
    schema: '../persistence/schemas/planeamentoSchema',
  };

  const roleController = {
    name: config.controllers.role.name,
    path: config.controllers.role.path,
  };

  const percursoController = {
    name: config.controllers.percurso.name,
    path: config.controllers.percurso.path,
  };

  const planeamentoController = {
    name: config.controllers.planeamento.name,
    path: config.controllers.planeamento.path,
  };

  const camiaoController = {
    name: config.controllers.camiao.name,
    path: config.controllers.camiao.path,
  };

  const roleRepo = {
    name: config.repos.role.name,
    path: config.repos.role.path,
  };

  const percursoRepo = {
    name: config.repos.percurso.name,
    path: config.repos.percurso.path,
  };

  const camiaoRepo = {
    name: config.repos.camiao.name,
    path: config.repos.camiao.path,
  };

  const planeamentoRepo = {
    name: config.repos.planeamento.name,
    path: config.repos.planeamento.path,
  };

  const userRepo = {
    name: config.repos.user.name,
    path: config.repos.user.path,
  };

  const camiaoService = {
    name: config.services.camiao.name,
    path: config.services.camiao.path,
  };

  const roleService = {
    name: config.services.role.name,
    path: config.services.role.path,
  };

  const percursoService = {
    name: config.services.percurso.name,
    path: config.services.percurso.path,
  };
  const planeamentoService = {
    name: config.services.planeamento.name,
    path: config.services.planeamento.path,
  };

  await dependencyInjectorLoader({
    mongoConnection,
    schemas: [userSchema, roleSchema, camiaoSchema, percursoSchema, planeamentoSchema],
    controllers: [camiaoController, roleController, percursoController, planeamentoController],
    repos: [camiaoRepo, roleRepo, userRepo, percursoRepo, planeamentoRepo],
    services: [camiaoService, roleService, percursoService, planeamentoService],
  });
  Logger.info('✌️ Schemas, Controllers, Repositories, Services, etc. loaded');

  await expressLoader({ app: expressApp });
  Logger.info('✌️ Express loaded');
};
