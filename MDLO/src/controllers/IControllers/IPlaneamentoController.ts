/* eslint-disable prettier/prettier */
import { Request, Response, NextFunction } from 'express';
import { Http2ServerResponse } from 'http2';

export default interface IPlaneamentoController {
  sendPlaneamento(req: Request, res: Response, next: NextFunction);
  sendPlaneamentoHeuristica1(req: Request, res: Response, next: NextFunction);
  sendPlaneamentoHeuristica2(req: Request, res: Response, next: NextFunction);
  sendPlaneamentoHeuristica3(req: Request, res: Response, next: NextFunction);
  sendPlaneamentoMock(req: Request, res: Response, next: NextFunction);
  sendPlaneamentoAlgoritmoGenetico(req: Request, res: Response, next: NextFunction);
  sendPlaneamentoAlgoritmoGeneticoFrota(req: Request, res: Response, next: NextFunction);
  getAllPlaneamentos(req: Request, res: Response, next: NextFunction);
}
