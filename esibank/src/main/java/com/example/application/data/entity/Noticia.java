package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "noticia")
public class Noticia extends AbstractEntity {
	private String tittle;
	private String paragraph;
	private String text;
	private String url;
	
	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Noticia(String tittle, String paragraph, String text, String url) {
		super();
		this.tittle = tittle;
		this.paragraph = paragraph;
		this.text = text;
		this.url = url;
	}

	public Noticia() {}
}