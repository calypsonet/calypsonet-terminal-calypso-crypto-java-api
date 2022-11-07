/* **************************************************************************************
 * Copyright (c) 2022 Calypso Networks Association https://calypsonet.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.calypsonet.terminal.calypso.crypto;

import java.util.List;
import org.calypsonet.terminal.calypso.card.CalypsoCard;

/**
 * Calypso card cryptography service.
 *
 * <p>This interface defines the API needed by a terminal to perform the cryptographic operations
 * required by a Calypso card.
 *
 * @since 1.0.0
 */
public interface CardCryptoService {

  /**
   * Initializes the crypto service context for operating a Secure Session with a card et gets the
   * terminal challenge.
   *
   * @param calypsoCard The Calypso card.
   * @param cardChallenge The card challenge.
   * @return The terminal challenge.
   * @since 1.0.0
   */
  byte[] processTerminalSecureSessionContextInitialization(
      CalypsoCard calypsoCard, byte[] cardChallenge);

  /**
   * Stores the data needed to initialize the digest computation for a Secure Session.
   *
   * @param openSecureSessionCommandDataOut The data out from the card Open Secure Session command.
   * @param kif The card KIF.
   * @param kvc The card KVC.
   * @since 1.0.0
   */
  void prepareDigestInitialization(byte[] openSecureSessionCommandDataOut, byte kif, byte kvc);

  /**
   * Gets the terminal certificate used for an early mutual authentication.
   *
   * @return The terminal certificate.
   * @since 1.0.0
   */
  byte[] processTerminalCertificateGeneration();

  /**
   * Stores the data needed to update the digest computation for a regular Secure Session, the
   * actual digest computation may be performed later.
   *
   * @param apduDataIn A not empty list of APDU command data in.
   * @param apduDataOut A not empty list of APDU command data out.
   * @since 1.0.0
   */
  void prepareDigestUpdate(List<byte[]> apduDataIn, List<byte[]> apduDataOut);

  /**
   * Updates the digest computation for an encrypted Secure Session and gets back either the command
   * to be sent to the card or the decrypted data of the response received from the card.
   *
   * @param apduData A byte array containing either the input or output data of a card command APDU.
   * @return Either the ciphered or deciphered command data.
   * @since 1.0.0
   */
  byte[] processDigestUpdateWithEncryptedCardExchangeData(byte[] apduData);

  /**
   * Finalizes the digest computation and returns the terminal part of the Secure Session
   * certificate.
   *
   * @return The terminal certificate
   * @since 1.0.0
   */
  byte[] processDigestFinalComputation();

  /**
   * Verifies the card certificate.
   *
   * @param cardCertificate The card certificate.
   * @return true if the card certificate is validated.
   * @since 1.0.0
   */
  boolean processCardCertificateVerification(byte[] cardCertificate);

  /**
   * Computes the needed data to operate SV card commands.
   *
   * @param svData
   * @return SV card command data.
   * @since 1.0.0
   */
  byte[] processSvSecurityDataComputation(byte[] svData);

  /**
   * Verifies the SV card certificate.
   *
   * @param svCardCertificate The SV card certificate.
   * @return true if the SV card certificate is validated.
   * @since 1.0.0
   */
  boolean processSvCardCertificateVerification(byte[] svCardCertificate);

  /**
   * Computes a block of encrypted data to be sent to the card for an enciphered PIN presentation.
   *
   * @param pin The 4-byte PIN value.
   * @param kif The PIN encryption key KIF.
   * @param kvc The PIN encryption key KVC.
   * @return The encrypted data block to sent to the card.
   * @since 1.0.0
   */
  byte[] processPinCipheringForPresentation(byte[] pin, byte kif, byte kvc);

  /**
   * Computes a block of encrypted data to be sent to the card for a PIN modification.
   *
   * @param currentPin The 4-byte current PIN value.
   * @param newPin The 4-byte new PIN value.
   * @param kif The PIN encryption key KIF.
   * @param kvc The PIN encryption key KVC.
   * @return The encrypted data block to sent to the card.
   * @since 1.0.0
   */
  byte[] processPinCipheringForModification(byte[] currentPin, byte[] newPin, byte kif, byte kvc);

  /**
   * Generates an encrypted key data block for loading a key into a card.
   *
   * @param issuerKeyKif The issuer key KIF.
   * @param issuerKeyKvc The issuer key KVC.
   * @param targetKeyKif The target key KIF.
   * @param targetKeyKvc The target key KVC.
   * @return The encrypted data block to sent to the card.
   * @since 1.0.0
   */
  byte[] processCardKeyGeneration(
      byte issuerKeyKif, byte issuerKeyKvc, byte targetKeyKif, byte targetKeyKvc);
}
