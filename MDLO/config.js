import dotenv from 'dotenv';
import path from 'path';

// Set the NODE_ENV to 'development' by default
process.env.NODE_ENV = process.env.NODE_ENV || 'development';

const envFound = dotenv.config();
if (!envFound) {
  // This error should crash whole process

  throw new Error("⚠️  Couldn't find .env file  ⚠️");
}

export default {
  prolog: 'https://localhost:5005',
  /**
   * Your favorite port
   */
  port: parseInt(process.env.PORT, 10) || 3000,

  /**
   * That long string from mlab
   */
  databaseURL:
    process.env.MONGODB_URI || 'mongodb+srv://admin:Password@cluster0.wu1ia2a.mongodb.net/?retryWrites=true&w=majority',

  //mongodb+srv://admin:Password@cluster0.wu1ia2a.mongodb.net/?retryWrites=true&w=majority

  //mongodb://127.0.0.1:27017/test
  /**
   * Your secret sauce
   */
  jwtSecret: process.env.JWT_SECRET || 'my sakdfho2390asjod$%jl)!sdjas0i secret',

  /**
   * Used by winston logger
   */
  logs: {
    level: process.env.LOG_LEVEL || 'info',
  },

  /**
   * API configs
   */
  api: {
    prefix: '/api',
  },

  controllers: {
    role: {
      name: 'RoleController',
      path: '../controllers/roleController',
    },
    camiao: {
      name: 'CamiaoController',
      path: '../controllers/camiaoController',
    },
    percurso: {
      name: 'PercursoController',
      path: '../controllers/percursoController',
    },
    planeamento: {
      name: 'PlaneamentoController',
      path: '../controllers/planeamentoController',
    },
  },

  repos: {
    role: {
      name: 'RoleRepo',
      path: '../repos/roleRepo',
    },
    user: {
      name: 'UserRepo',
      path: '../repos/userRepo',
    },
    camiao: {
      name: 'CamiaoRepo',
      path: '../repos/camiaoRepo',
    },
    percurso: {
      name: 'PercursoRepo',
      path: '../repos/percursoRepo',
    },
    planeamento: {
      name: 'PlaneamentoRepo',
      path: '../repos/planeamentoRepo',
    },
  },

  services: {
    role: {
      name: 'RoleService',
      path: '../services/roleService',
    },
    camiao: {
      name: 'CamiaoService',
      path: '../services/camiaoService',
    },
    percurso: {
      name: 'PercursoService',
      path: '../services/percursoService',
    },
    planeamento: {
      name: 'PlaneamentoService',
      path: '../services/planeamentoService',
    },
  },
};
