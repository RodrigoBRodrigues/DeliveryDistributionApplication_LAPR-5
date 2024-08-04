import { Request, Response, NextFunction} from "express";
import { Http2ServerResponse } from "http2";

export default interface IPercursoController{
    createPercurso(req: Request, res: Response, next: NextFunction);
    getPercursoListByArmazemPartida(req: Request, res: Response, next: NextFunction);
    getPercursoById(req: Request, res: Response, next: NextFunction);
    getPercursoList(req: Request, res: Response, next: NextFunction);
    deletePercurso(req: Request, res: Response, next: NextFunction);
    getPercursosEntreArmazens(req: Request, res: Response, next: NextFunction);
    getPrimeiroPercursoEntreArmazens(req: Request, res: Response, next: NextFunction);
    updatePercurso(req: Request, res: Response, next: NextFunction)

}
