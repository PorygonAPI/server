package fatec.porygon.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "talhao")
    public class Talhao {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(columnDefinition = "jsonb")
        private String produtividadeAno;

        private Double area;

        @ManyToOne
        @JoinColumn(name = "tipo_solo_id")
        private tipoSolo tipoSolo;

        @Column(name = "area_agricola_id")
        private Long areaAgricolaId;

        @Column(columnDefinition = "geometry")
        private String arquivoDaninha;

        @Column(columnDefinition = "geometry")
        private String arquivoFinalDaninha;

        @Column(columnDefinition = "jsonb")
        private String arquivodaninha;

        @Column(columnDefinition = "jsonb")
        private String arquivofinaldaninha;

        @OneToMany(mappedBy = "talhao")
        private List<Safra> safras = new ArrayList<>();
    }

