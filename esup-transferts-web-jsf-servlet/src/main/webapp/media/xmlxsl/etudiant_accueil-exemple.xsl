<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

			<fo:layout-master-set>
				<fo:simple-page-master master-name="all"
					page-height="29.7cm" page-width="21cm" margin-top="0.5cm"
					margin-bottom="2cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body margin-top="2cm" margin-bottom="0cm" />
					<fo:region-before extent="5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="entete" />
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="miseEnPage" />
					</fo:block>
					<fo:block>
						<xsl:call-template name="exemplaireDU" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>

<!--
			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="entete" />
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="miseEnPage" />
					</fo:block>
					<fo:block>
						<xsl:call-template name="exemplaireUO" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
-->

<!--
			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="entete" />
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="miseEnPage" />
					</fo:block>
					<fo:block>
						<xsl:call-template name="exemplaireEtu" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
-->

		</fo:root>
	</xsl:template>

	<xsl:template name="entete">
		<fo:block>
			<fo:external-graphic src="logo_couleur_moyen.jpg"
				height="60px" width="100px" />
			<fo:block padding-top="-2.2cm" text-align="center"
				font-size="20pt">
				Demande de transfert
			</fo:block>
			<fo:block padding-top="0.2cm" text-align="center" font-size="20pt">
				Fiche d'accueil
			</fo:block>
			<fo:block padding-top="0.2cm" text-align="center" font-size="8pt">
				<xsl:text>Vous souhaitez vous inscrire à </xsl:text>
				<!-- <xsl:value-of select="EtudiantRefImp/universiteAccueil/libEtb" /> -->
				<xsl:value-of select="EtudiantRefImp/universiteAccueil/libOffEtb" />
			</fo:block>
		</fo:block>

		<fo:block padding-top="5pt" text-align="left" font-size="8pt">
			<fo:block>
				<xsl:value-of select="EtudiantRefImp/transferts/fichier/libelle" />
			</fo:block>
			<fo:block>
				<xsl:value-of select="EtudiantRefImp/transferts/fichier/adresse" />
			</fo:block>
			<fo:block>
				<xsl:value-of select="EtudiantRefImp/transferts/fichier/boitePostale" />
			</fo:block>
			<fo:block>
				<xsl:value-of select="EtudiantRefImp/transferts/fichier/codePostal" />
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template name="miseEnPage">
		<xsl:apply-templates select="EtudiantRefImp" />
	</xsl:template>

	<xsl:template match="EtudiantRefImp">
		<!-- <xsl:param name="avis" select="accueilDecision/avis" /> -->
		<xsl:param name="avis" select="codeDecision" />
		<fo:block padding-top="60pt">
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>INE (Identifiant National Etudiant) : </xsl:text>
								<xsl:value-of select="numeroIne" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Nom de famille : </xsl:text>
								<xsl:value-of select="nomPatronymique" />
								<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
								<xsl:text>Prénom : </xsl:text>
								<xsl:value-of select="prenom1" />
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Nom d'usage : </xsl:text>
								<xsl:value-of select="nomUsuel" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Née le : </xsl:text>
								<xsl:value-of select="substring(dateNaissance,9,2)" />
								/
								<xsl:value-of select="substring(dateNaissance,6,2)" />
								/
								<xsl:value-of select="substring(dateNaissance,0,5)" />
								<!-- <xsl:value-of select="dateNaissance" /> -->
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Nationalité : </xsl:text>
								<xsl:value-of select="libNationalite" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Adresse : </xsl:text>
								<xsl:value-of select="adresse/libAd1" />
								<xsl:text>&#160;&#160;</xsl:text>
								<xsl:value-of select="adresse/libAd2" />
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
								<xsl:value-of select="adresse/libAd3" />
							</fo:block>
							<xsl:if test="(adresse/codPay = '100')">
								<fo:block font-size="9pt">
									<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
									<xsl:value-of select="adresse/codePostal" />
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
									<xsl:value-of select="adresse/nomCommune" />
								</fo:block>
							</xsl:if>
							<xsl:if test="(adresse/codPay != '100')">
								<fo:block font-size="9pt">
									<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
									<xsl:value-of select="adresse/codeVilleEtranger" />
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
									<xsl:value-of select="adresse/libPay" />
								</fo:block>
							</xsl:if>
						</fo:table-cell>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Téléphones : </xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>- personnel : </xsl:text>
								<xsl:value-of select="adresse/numTel" />
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>- portable : </xsl:text>
								<xsl:value-of select="adresse/numTelPortable" />
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>- Adresse mail : </xsl:text>
								<xsl:value-of select="adresse/email" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Sollicite le transfert de :  </xsl:text>
								<xsl:value-of select="universiteDepart/libEtb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="universiteDepart/libAd1Etb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="universiteDepart/codPosAdrEtb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="universiteDepart/libAch" />
								<!-- <xsl:text> - </xsl:text> -->
								<!-- <xsl:value-of select="universiteDepart/libDep" /> -->
								<!-- <xsl:text> (</xsl:text> -->
								<!-- <xsl:value-of select="universiteDepart/codeDep" /> -->
								<!-- <xsl:text>)</xsl:text> -->
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Pour l'université :  </xsl:text>
								<xsl:value-of select="universiteAccueil/libEtb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="universiteAccueil/libAd1Etb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="universiteAccueil/codPosAdrEtb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="universiteAccueil/libAch" />
								<!-- <xsl:text> - </xsl:text> -->
								<!-- <xsl:value-of select="universiteAccueil/libDep" /> -->
								<!-- <xsl:text> (</xsl:text> -->
								<!-- <xsl:value-of select="universiteAccueil/codeDep" /> -->
								<!-- <xsl:text>)</xsl:text> -->
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>En vue d'une inscription en :  </xsl:text>
								<xsl:if test="(transferts/odf != '')">
									<xsl:value-of select="transferts/odf/libVersionEtape" />
								</xsl:if>
								<xsl:if test="(transferts/libelleTypeDiplome != '')">
									<xsl:value-of select="transferts/libelleTypeDiplome" />
								</xsl:if>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt" text-align="center">
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>SITUATION UNIVERSITAIRE</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block padding-top="5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>BACCALAUREAT : </xsl:text>
								<xsl:value-of select="trBac/libBac" />
								<xsl:text> - année d'obtention : </xsl:text>
								<xsl:value-of select="trBac/anneeObtentionBac" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column column-width="19mm" />
				<fo:table-column column-width="125mm" />
				<fo:table-column column-width="46mm" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="0.019cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Année</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Cycle et année d'étude</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Résultats</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:for-each select="//situationUniversitaire">
						<fo:table-row border="1">
							<fo:table-cell border="0.018cm solid #000000">
								<fo:block font-size="8pt">
									<xsl:value-of select="annee" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="0.018cm solid #000000">
								<fo:block font-size="8pt">
									<xsl:value-of select="libelle" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="0.018cm solid #000000">
								<fo:block font-size="8pt">
									<xsl:value-of select="resultat" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:for-each>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>- L'étudiant(e) est-il inscrit(e) à des examens de juin (1ère session) : .................... session de rattrapage : ......................</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="-5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>- Avez-vous effectué une validation d'études pour vous inscrire à l'</xsl:text>
								<xsl:value-of select="universiteAccueil/libEtb" />
								<xsl:text>, si oui précisez laquelle :</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="-5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>..............................................................................................................................................................................................</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="-5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>- Quels sont les motifs exposés par l'étudiant pour le transfert de son dossier ? ................................................................</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="-5pt">
			<fo:table padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>..............................................................................................................................................................................................</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column column-width="63.3mm" />
				<fo:table-column column-width="63.3mm" />
				<fo:table-column column-width="63.3mm" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text>&#160;[&#160;] : Avis favorable</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;[&#160;] : Avis défavorable - </xsl:text>
								<xsl:text>&#160;Motif : </xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;du(de la) Président(e) de l'université d'origine</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<xsl:if test="$avis = 'A'">
								<fo:block font-size="9pt">
									<xsl:text>&#160;[X] : </xsl:text>
									<xsl:value-of select="/EtudiantRefImp/decision" />
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;</xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;[&#160;] : Avis défavorable - </xsl:text>
									<xsl:text>&#160;Motif : </xsl:text>
								</fo:block>
							</xsl:if>
							<xsl:if test="$avis = 'B'">
								<fo:block font-size="9pt">
									<xsl:text>&#160;[&#160;] : Avis favorable</xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;</xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;[X] : Avis défavorable - </xsl:text>
									<xsl:text>&#160;Motif : </xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;</xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:value-of select="/EtudiantRefImp/decision" />
								</fo:block>
							</xsl:if>
							<xsl:if test="$avis=(.='')">
								<fo:block font-size="9pt">
									<xsl:text>&#160;[&#160;] : Avis favorable</xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;</xsl:text>
								</fo:block>
								<fo:block font-size="9pt">
									<xsl:text>&#160;[&#160;] : Avis défavorable</xsl:text>
									<xsl:text>&#160;Motif : </xsl:text>
								</fo:block>
							</xsl:if>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;du(de la) Président(e) de l'université d'accueil</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text>&#160;Signature de l'étudiant</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row height="4cm">
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;DATE : </xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;DATE : </xsl:text>
								<xsl:value-of select="substring(dateDuJour,9,2)" />
								/
								<xsl:value-of select="substring(dateDuJour,6,2)" />
								/
								<xsl:value-of select="substring(dateDuJour,0,5)" />
							</fo:block>
							<fo:block margin-left="0.2cm" text-align="center">
								<xsl:if test="/EtudiantRefImp/transferts/temoinTransfertValide=2">
									<xsl:if test="$avis=(.!='')">
										<fo:external-graphic content-width="1cm"
											content-height="1cm" height="70px" width="120px">
											<xsl:attribute name="src">
										<xsl:value-of select="/EtudiantRefImp/transferts/fichier/chemin" />
									</xsl:attribute>
										</fo:external-graphic>
									</xsl:if>
								</xsl:if>
								<!-- <xsl:value-of select="transferts/fichier/nom" /> -->
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;DATE : </xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
<!--
					<fo:table-row>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="7pt" font-style="italic" text-align="justify">
								<xsl:text>Cette démarche n'est pas utile si vous êtes en possession de l'attestation de transfert "départ" signée de votre établissement d'origine. Les documents seront à joindre à votre dossier d'inscription administrative</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text></xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="9pt">
								<xsl:text></xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
-->
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block text-align="left" font-size="9pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">
			L'intéressé(e) déclare sur l'honneur que les
			renseignements figurant ci-dessus sont exacts et qu'il ne fait
			l'objet d'aucune peine disciplinaire ou d'exclusion.
		</fo:block>
		<fo:block font-size="9pt">
			<xsl:text>&#160;</xsl:text>
		</fo:block>
	</xsl:template>

	<xsl:template name="exemplaireDU">
		<fo:block text-align="left" font-size="8pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">
			- Exemplaire destiné à la Direction des Etudes
		</fo:block>
		<fo:block>
			<xsl:text>&#160;</xsl:text>
		</fo:block>
		<fo:block font-size="6pt" text-align="center">
			PAGE -
			<fo:page-number />
		</fo:block>
	</xsl:template>

<!--
	<xsl:template name="exemplaireUA">
		<fo:block text-align="left" font-size="8pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">
			- Exemplaire destiné à l'université d'accueil
		</fo:block>
		<fo:block>
			<xsl:text>&#160;</xsl:text>
		</fo:block>
		<fo:block font-size="6pt" text-align="center">
			PAGE -
			<fo:page-number />
		</fo:block>
	</xsl:template>
-->

<!--
	<xsl:template name="exemplaireUO">
		<fo:block text-align="left" font-size="8pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">
			- Exemplaire destiné à l'université d'origine
		</fo:block>
		<fo:block>
			<xsl:text>&#160;</xsl:text>
		</fo:block>
		<fo:block font-size="6pt" text-align="center">
			PAGE -
			<fo:page-number />
		</fo:block>
	</xsl:template>
-->

	<xsl:template name="exemplaireEtu">
		<fo:block text-align="left" font-size="8pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">
			- Exemplaire destiné à l'étudiant
		</fo:block>
		<fo:block>
			<xsl:text>&#160;</xsl:text>
		</fo:block>
		<fo:block font-size="6pt" text-align="center">
			PAGE -
			<fo:page-number />
		</fo:block>
	</xsl:template>
</xsl:stylesheet>