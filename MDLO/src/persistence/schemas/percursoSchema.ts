import { IPercursoPersistence } from '../../dataschema/IPercursoPersistence';
import mongoose from 'mongoose';

const Percurso = new mongoose.Schema(
  {
        domainId: { 
        type: String,
        unique: true
        },

        armazemPartida: {
            type: String,
            required: [true, 'Porfavor introduza a localizacao inicial'],
            index: true,
        },

        armazemChegada: {
            type: String,
            required: [true, 'Porfavor introduza a localizacao final'],
            index: true,
        },

        distancia: {
            type: Number,
            required: [true, 'Porfavor introduza a distancia'],
            index: true,
        },

        tempo: {
            type: Number,
            required: [true, 'Porfavor introduza o tempo'],
            index: true,
        },

        energiaGasta: {
            type: Number,
            required: [true, 'Porfavor introduza a energia gasta'],
            index: true,
        },

        tempoExtra: {
            type: Number,
            required: [true, 'Porfavor introduza o tempo extra'],
            index: true,
        },
    },
    { timestamps: true },

);

export default mongoose.model<IPercursoPersistence & mongoose.Document>('Percurso', Percurso);
