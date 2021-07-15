package mz.unilurio.solidermed.model;

public enum GestatinalRange {
    MENORQUE28SEMANAS{
        public String toString(){
            return "28 Semanas";
        }
    },
    ENTRE28E31SEMANAS{
        public String toString(){
            return "entre 28 a 31 Semanas";
        }
    },
    ENTRE32E36SEMANAS{
        public String toString(){
            return "entre 32 a 36 Semanas";
        }
    },
    MAIORQUE37SEMANAS{
        public String toString(){
            return ">36 Semanas";
        }
    };
}
