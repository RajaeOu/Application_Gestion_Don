package com.geoinfo.gestiondon.model;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;



@Entity
@Table(name = "Commune")
public class Commune {
@Id
private int id;

private Geometry geom;

private int idcom;

private String commune;

private int popu;

private int popr;

private int idprov;

private String province;

private int idreg;

private String region;

private double x_centroid;

private double y_centroid;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIdcom() {
	return idcom;
}
public void setIdcom(int idcom) {
	this.idcom = idcom;
}
public String getCommune() {
	return commune;
}
public void setCommune(String commune) {
	this.commune = commune;
}
public int getPopu() {
	return popu;
}
public void setPopu(int popu) {
	this.popu = popu;
}
public String getProvince() {
	return province;
}
public void setProvince(String province) {
	this.province = province;
}
public String getRegion() {
	return region;
}
public void setRegion(String region) {
	this.region = region;
}
public double getX_centroid() {
	return x_centroid;
}
public void setX_centroid(double x_centroid) {
	this.x_centroid = x_centroid;
}
public double getY_centroid() {
	return y_centroid;
}
public void setY_centroid(double y_centroid) {
	this.y_centroid = y_centroid;
}

public Commune(int id, Geometry geom, int idcom, String commune, int popu, int popr, int idprov, String province,
		int idreg, String region, double x_centroid, double y_centroid) {
	super();
	this.id = id;
	this.geom = geom;
	this.idcom = idcom;
	this.commune = commune;
	this.popu = popu;
	this.popr = popr;
	this.idprov = idprov;
	this.province = province;
	this.idreg = idreg;
	this.region = region;
	this.x_centroid = x_centroid;
	this.y_centroid = y_centroid;
}


}
