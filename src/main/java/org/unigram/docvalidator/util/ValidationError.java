/**
 * DocumentValidator
 * Copyright (c) 2013-, Takahiko Ito, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package org.unigram.docvalidator.util;

import org.unigram.docvalidator.store.Sentence;

/**
 * Error to report invalid point from Validators.
 */
public class ValidationError {

  public ValidationError(String errorMessage) {
    super();
    this.lineNumber = -1;
    this.message = errorMessage;
    this.fileName = "";
    this.sentence = null;
  }

  public ValidationError(int errorLineNumber, String errorMessage) {
    this(errorMessage);
    this.lineNumber = errorLineNumber;
    this.fileName = "";
  }

  public ValidationError(String errorMessage,
      Sentence sentence) {
    this(sentence.position, errorMessage);
    this.sentence = sentence;
  }

  public ValidationError(int errorLineNumber, String errorMessage,
      String errorFileName) {
    this(errorLineNumber, errorMessage);
    this.fileName = errorFileName;
  }

  public ValidationError(String errorMessage,
      Sentence sentence, String erorFileName) {
    this(sentence.position, errorMessage);
    this.sentence = sentence;
    this.fileName = erorFileName;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int errorLineNumber) {
    this.lineNumber = errorLineNumber;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String errorMessage) {
    this.message = errorMessage;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String erroFileName) {
    this.fileName = erroFileName;
  }

  public Sentence getSentence() {
    return sentence;
  }

  public void setSentence(Sentence sentence) {
    this.sentence = sentence;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    if (this.fileName == null || this.fileName.equals("")) {
      str.append("ValidationError[" + lineNumber + " (" + message+ ")]");
    } else {
      str.append("ValidationError[" + this.fileName +
          lineNumber + " (" + message+ ")]");
    }
    if(this.sentence != null) {
      str.append(" at line: " + sentence.content);
    }
    return str.toString();
  }

  private int lineNumber;

  private String message;

  private String fileName;

  private Sentence sentence;
}
