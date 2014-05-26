/**
 * redpen: a text inspection tool
 * Copyright (C) 2014 Recruit Technologies Co., Ltd. and contributors
 * (see CONTRIBUTORS.md)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bigram.docvalidator.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bigram.docvalidator.DocumentValidatorException;
import org.bigram.docvalidator.config.CharacterTable;
import org.bigram.docvalidator.model.DocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bigram.docvalidator.config.Configuration;
import org.bigram.docvalidator.symbol.DefaultSymbols;

/**
 * Abstract Parser class containing common procedures to
 * implements the concrete Parser classes.
 */
public abstract class BasicDocumentParser implements Parser {
  /**
   * Given configuration , return basic configuration settings.
   *
   * @param configuration object containing configuration settings
   * @param documentBuilder Builder object of DocumentCollection
   */
  public final void initialize(Configuration configuration,
      DocumentCollection.Builder documentBuilder) throws
      DocumentValidatorException {
    if (configuration == null) {
      throw new DocumentValidatorException("Given configuration is null");
    }
    if (configuration.getCharacterTable() == null) {
      throw new DocumentValidatorException(
          "Character table in the given configuration is null");
    }

    CharacterTable characterTable = configuration.getCharacterTable();

    // set full stop characters
    if (characterTable.isContainCharacter("FULL_STOP")) {
      this.periods.add(
          characterTable.getCharacter("FULL_STOP").getValue());
    } else {
      this.periods.add(
          DefaultSymbols.getInstance().get("FULL_STOP").getValue());
    }

    if (characterTable.isContainCharacter("QUESTION_MARK")) {
      this.periods.add(
          characterTable.getCharacter("QUESTION_MARK").getValue());
    } else {
      this.periods.add(
          DefaultSymbols.getInstance().get("QUESTION_MARK").getValue());
    }

    if (characterTable.isContainCharacter("EXCLAMATION_MARK")) {
      this.periods.add(
          characterTable.getCharacter("EXCLAMATION_MARK").getValue());
    } else {
      this.periods.add(
          DefaultSymbols.getInstance().get("EXCLAMATION_MARK").getValue());
    }

    for (String period : this.periods) {
      LOG.info("\"" + period + "\" is added as a end of sentence character");
    }

    this.sentenceExtractor = new SentenceExtractor(this.periods);
    this.builder = documentBuilder;
  }

  /**
   * create BufferedReader from InputStream is.
   * @param is InputStream using to parse
   * @return BufferedReader created from InputStream
   * @throws DocumentValidatorException if InputStream is not
   * supported UTF-8 encoding
   */
  protected BufferedReader createReader(InputStream is)
      throws DocumentValidatorException {
    if (is == null) {
      throw new DocumentValidatorException("input stream is null");
    }
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new DocumentValidatorException(
          "does not support UTF-8 encoding", e);
    }
    return br;
  }

  protected final InputStream loadStream(String fileName)
      throws DocumentValidatorException {
    InputStream inputStream;
    if (fileName == null || fileName.equals("")) {
      throw new DocumentValidatorException("input file was not specified.");
    } else {
      try {
        inputStream = new FileInputStream(fileName);
      } catch (FileNotFoundException e) {
        throw new DocumentValidatorException("Input file is not found", e);
      }
    }
    return inputStream;
  }

  /**
   * Get SentenceExtractor object.
   *
   * @return sentence extractor object
   */
  protected SentenceExtractor getSentenceExtractor() {
    return sentenceExtractor;
  }

  protected DocumentCollection.Builder builder;

  private SentenceExtractor sentenceExtractor;

  private List<String> periods = new ArrayList<String>();

  private static final Logger LOG = LoggerFactory.getLogger(
      BasicDocumentParser.class);


}
