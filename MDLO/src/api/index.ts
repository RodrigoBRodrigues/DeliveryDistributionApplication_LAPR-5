import { Router } from 'express';
import auth from './routes/userRoute';
import user from './routes/userRoute';
import role from './routes/roleRoute';
import camiao from './routes/camiaoRoute';
import percurso from './routes/percursoRoute'
import planeamento from './routes/planeamentoRoute';

export default () => {
	const app = Router();

	auth(app);
	user(app);
	role(app);
	camiao(app);
	percurso(app);
	planeamento(app);

	return app
}
