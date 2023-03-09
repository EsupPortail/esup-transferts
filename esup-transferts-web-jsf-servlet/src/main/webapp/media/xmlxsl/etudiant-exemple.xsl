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
					<fo:region-before extent="3cm" />
					<!-- <fo:region-after extent="0cm" /> -->
				</fo:simple-page-master>
			</fo:layout-master-set>

<!--
			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="entete" />
				</fo:static-content>
-->
				<!-- <fo:static-content flow-name="xsl-region-after"> -->
				<!-- <xsl:call-template name="basDePage" /> -->
				<!-- </fo:static-content> -->
<!--
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="miseEnPage" />
					</fo:block>
					<fo:block>
						<xsl:call-template name="avisEtabDepart" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
-->

			<fo:page-sequence master-reference="all">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="entete" />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after">
					<xsl:call-template name="basDePage" />
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<xsl:call-template name="miseEnPage" />
					</fo:block>
					<fo:block>
						<xsl:call-template name="avisEtabAccueil" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template name="entete">
		<fo:block>
			<fo:external-graphic src="logo_couleur_moyen.jpg"
				height="40px" width="50px" />
		</fo:block>
		<fo:block text-align="center" font-size="9pt" margin-right="11cm"
			padding-top="-1.2cm">
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
		<fo:block text-align="center" font-size="9pt" margin-left="12cm"
			padding-top="-2cm">
			<fo:block>
				Demande de transfert départ
			</fo:block>
			<fo:block>
				DOSSIER UNIVERSITAIRE
				<xsl:value-of select="EtudiantRefImp/anneeUniversitaire" />
			</fo:block>
			<xsl:if test="(EtudiantRefImp/transferts/typeTransfert = 'T')">
				<fo:block>
					Transfert
					<xsl:value-of select="EtudiantRefImp/transferts/libTypeTransfert" />
					: vous souhaitez vous réinscrire uniquement à l'
					<xsl:value-of select="EtudiantRefImp/universiteAccueil/libEtb" />
				</fo:block>
			</xsl:if>
			<xsl:if test="(EtudiantRefImp/transferts/typeTransfert = 'P')">
				<fo:block font-size="8pt">
					Transfert
					<xsl:value-of select="EtudiantRefImp/transferts/libTypeTransfert" />
					: vous souhaitez conserver une inscription à l'
					<xsl:value-of select="EtudiantRefImp/universiteDepart/libEtb" />
					et prendre une autre inscription à l'
					<xsl:value-of select="EtudiantRefImp/universiteAccueil/libEtb" />
				</fo:block>
			</xsl:if>
			<fo:block>
				DOSSIER SAISI LE :
				<xsl:value-of
					select="substring(EtudiantRefImp/transferts/dateDemandeTransfert,9,2)" />
				/
				<xsl:value-of
					select="substring(EtudiantRefImp/transferts/dateDemandeTransfert,6,2)" />
				/
				<xsl:value-of
					select="substring(EtudiantRefImp/transferts/dateDemandeTransfert,0,5)" />
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template name="miseEnPage">
		<xsl:apply-templates select="EtudiantRefImp" />
	</xsl:template>

	<xsl:template match="EtudiantRefImp">
		<xsl:param name="notnull" select="/EtudiantRefImp/trResultatVdiVetDTO" />
		<xsl:variable name="compteur">
			<xsl:for-each select="//trResultatVdiVetDTO/etapes">
				<xsl:if test="position()=last()">
					<xsl:value-of select="last()" />
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>

		<fo:block padding-top="5pt">
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
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>N° de carte étudiant : </xsl:text>
								<xsl:value-of select="numeroEtudiant" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
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
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
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
								<xsl:value-of
									select="universiteDepart/codPosAdrEtb" />
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
								<xsl:value-of
									select="universiteAccueil/codPosAdrEtb" />
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
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
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
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>BACCALAUREAT : </xsl:text>
								<xsl:value-of select="trBac/libBac" />
								<xsl:text> - Académie : </xsl:text>
								<xsl:value-of select="trBac/libelleAcademie" />
								<xsl:text> - année d'obtention : </xsl:text>
								<xsl:value-of select="trBac/anneeObtentionBac" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column column-width="19mm" />
				<fo:table-column column-width="83mm" />
				<fo:table-column column-width="31mm" />
				<fo:table-column column-width="13mm" />
				<fo:table-column column-width="31mm" />
				<fo:table-column column-width="13mm" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Inscription</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Etapes</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Session 1</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Mention 1</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Session 2</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="0.018cm solid #000000">
							<fo:block font-size="8pt">
								<xsl:text>Mention 2</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:for-each select="//trResultatVdiVetDTO/etapes">
						<fo:table-row border="1">
							<fo:table-cell border="0.018cm solid #000000">
								<fo:block font-size="8pt">
									<xsl:value-of select="annee" />
									<!-- <xsl:text> - </xsl:text> -->
									<!-- <xsl:value-of select="position()" /> -->
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="0.018cm solid #000000">
								<fo:block font-size="8pt">
									<xsl:value-of select="libEtape" />
								</fo:block>
							</fo:table-cell>
							<xsl:for-each select="session">
							<!--<xsl:sort select="libSession" order="ascending" />-->
								<fo:table-cell border="0.018cm solid #000000">
									<fo:block font-size="8pt">
										<xsl:value-of select="resultat" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell border="0.018cm solid #000000">
									<fo:block font-size="8pt">
										<xsl:value-of select="mention" />
									</fo:block>
								</fo:table-cell>
							</xsl:for-each>
						</fo:table-row>
					</xsl:for-each>


					<xsl:choose>
						<!-- <xsl:when test="$notnull"> -->
						<xsl:when
							test="($notnull) and (/EtudiantRefImp/trResultatVdiVetDTO = '')">
							<fo:table-row border="1">
								<fo:table-cell number-columns-spanned="6">
									<fo:block font-size="8pt">
										<xsl:text>&#160;</xsl:text>
									</fo:block>
									<fo:block font-size="12pt">
										<xsl:text>Etat néant</xsl:text>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:when>
					</xsl:choose>
					<xsl:call-template name="bouclefor">
						<xsl:with-param name="min">
							<xsl:value-of select="$compteur" />
						</xsl:with-param>
						<xsl:with-param name="max">
							15
						</xsl:with-param>
					</xsl:call-template>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template name="avisEtabDepart">
		<xsl:param name="avis" select="/EtudiantRefImp/avis/idDecisionDossier" />
		<!-- Début de test template -->
		<fo:block padding-top="5pt" text-align="center">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1" font-weight="bold">
							<fo:block font-size="9pt">
								<xsl:text>Ce volet est a remettre à : </xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteDepart/libEtb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteDepart/libDep" />
								<xsl:text> (</xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteDepart/codeDep" />
								<xsl:text>)</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block padding-top="5pt">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Le </xsl:text>
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,9,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,6,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,0,5)" />
								<xsl:text> à </xsl:text>
								<xsl:text>....................................</xsl:text>
								<xsl:text>Signature de l'étudiant (obligatoire) : </xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block padding-top="5pt">

			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Avis du(de la) Président(e) de l'</xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteDepart/libEtb" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<xsl:if test="$avis = '2'">
								<fo:block font-size="9pt">
									<xsl:text>[X] Favorable</xsl:text>
								</fo:block>
							</xsl:if>
							<xsl:if test="$avis != '2' or (/EtudiantRefImp/avis/id = '0')">
								<fo:block font-size="9pt">
									<xsl:text>[] Favorable</xsl:text>
								</fo:block>
							</xsl:if>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<xsl:if test="$avis = '1'">
								<fo:block font-size="9pt">
									<xsl:text>[X] Défavorable, motif : </xsl:text>
									<xsl:value-of select="/EtudiantRefImp/avis/motifDecisionDossier" />
								</fo:block>
							</xsl:if>
							<xsl:if test="$avis != '1' or (/EtudiantRefImp/avis/id = '0')">
								<fo:block font-size="9pt">
									<xsl:text>[] Défavorable, motif :</xsl:text>
								</fo:block>
							</xsl:if>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<!-- <fo:table-row> -->
					<!-- <fo:table-cell border="1"> -->
					<!-- <fo:block font-size="9pt"> -->
					<!-- <xsl:text>&#160;</xsl:text> -->
					<!-- </fo:block> -->
					<!-- </fo:table-cell> -->
					<!-- </fo:table-row> -->
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Le : </xsl:text>
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,9,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,6,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,0,5)" />
								<xsl:text>&#160;&#160;&#160;&#160;&#160;</xsl:text>
								<xsl:text>Timbre de l'Université</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block margin-left="0.2cm" text-align="center">
								<xsl:if test="$avis=(.!='0')">
									<fo:external-graphic content-width="1cm"
										content-height="1cm" height="70px" width="120px">
										<xsl:attribute name="src">
										<xsl:value-of select="/EtudiantRefImp/transferts/fichier/chemin" />
									</xsl:attribute>
									</fo:external-graphic>
								</xsl:if>
								<!-- <xsl:value-of select="transferts/fichier/nom" /> -->
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block text-align="center" font-size="6pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">

			La loi 7817 du 6 janvier 1978 relative à
			l'informatique, aux
			fichiers et aux libertés s'applique à ce dossier.
			Elle vous donne le
			droit d'accès et de rectification sur les données
			vous concernant.
		</fo:block>
		<!-- Fin de test template -->
	</xsl:template>

	<xsl:template name="avisEtabAccueil">
		<xsl:param name="avis" select="/EtudiantRefImp/avis/idDecisionDossier" />
		<!-- Début de test template -->
		<fo:block padding-top="5pt" text-align="center">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1" font-weight="bold">
							<fo:block font-size="9pt">
								<xsl:text>Ce volet est a remettre à : </xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteAccueil/libEtb" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteAccueil/libDep" />
								<xsl:text> (</xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteAccueil/codeDep" />
								<xsl:text>)</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block padding-top="5pt">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Le </xsl:text>
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,9,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,6,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,0,5)" />
								<xsl:text> à </xsl:text>
								<xsl:text>....................................</xsl:text>
								<xsl:text>Signature de l'étudiant (obligatoire) : </xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block padding-top="5pt">
			<!-- -->
			<!--<fo:table height="{$margehaute}px" background-image="url({$baseMediaPdf}/images/logo.gif)" 
				background-repeat="no-repeat"> -->
			<fo:table border="0.018cm solid #000000" padding="3pt">
				<fo:table-column />
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>Avis du(de la) Président(e) de l'</xsl:text>
								<xsl:value-of select="/EtudiantRefImp/universiteDepart/libEtb" />
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1">
							<fo:block font-size="9pt">
								<xsl:text>ZONE RESERVEE A L'ADMINISTRATION :</xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1">
							<xsl:if test="$avis = '2'">
								<fo:block font-size="9pt">
									<xsl:text>[X] Favorable</xsl:text>
								</fo:block>
							</xsl:if>
							<xsl:if test="$avis != '2' or (/EtudiantRefImp/avis/id = '0')">
								<fo:block font-size="9pt">
									<xsl:text>[] Favorable</xsl:text>
								</fo:block>
							</xsl:if>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<xsl:if test="$avis = '1'">
								<fo:block font-size="9pt">
									<xsl:text>[X] Défavorable, motif : </xsl:text>
									<xsl:value-of select="/EtudiantRefImp/avis/motifDecisionDossier" />
								</fo:block>
							</xsl:if>
							<xsl:if test="$avis != '1' or (/EtudiantRefImp/avis/id = '0')">
								<fo:block font-size="9pt">
									<xsl:text>[] Défavorable, motif :</xsl:text>
								</fo:block>
							</xsl:if>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>Le : </xsl:text>
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,9,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,6,2)" />
								/
								<xsl:value-of select="substring(EtudiantRefImp/dateDuJour,0,5)" />
								<xsl:text>&#160;&#160;&#160;&#160;&#160;</xsl:text>
								<xsl:text>Timbre de l'Université</xsl:text>
							</fo:block>
							<fo:block font-size="9pt">
								<xsl:text>&#160;</xsl:text>
							</fo:block>
							<fo:block margin-left="0.2cm" text-align="center">
							<xsl:if test="$avis=(.!='0')">
								<fo:external-graphic content-width="1cm"
									content-height="1cm" height="70px" width="120px">
									<xsl:attribute name="src">
										<xsl:value-of select="/EtudiantRefImp/transferts/fichier/chemin" />
									</xsl:attribute>
								</fo:external-graphic>
							</xsl:if>
								<!-- <xsl:value-of select="transferts/fichier/nom" /> -->
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1">
							<fo:table border="0.018cm solid #000000" padding="3pt">
								<fo:table-column />
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell border="1">
											<fo:block font-size="9pt">
												<xsl:text>Avis du(de la) Président(e) de l'</xsl:text>
												<xsl:value-of select="/EtudiantRefImp/universiteAccueil/libEtb" />
												<!-- <xsl:text>Avis du Président de l'université de Valenciennes</xsl:text> -->
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>[] Favorable</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>[] Défavorable, motif :</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>.............................................</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>.............................................</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>Le : &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
												<xsl:text>Timbre de l'Université</xsl:text>
												<xsl:text>&#160;&#160;&#160;&#160;&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
											<fo:block font-size="9pt">
												<xsl:text>&#160;</xsl:text>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block text-align="center" font-size="6pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">

			La loi 7817 du 6 janvier 1978 relative à
			l'informatique, aux
			fichiers et aux libertés s'applique à ce dossier.
			Elle vous donne le
			droit d'accès et de rectification sur les données
			vous concernant.
		</fo:block>
		<!-- Fin de test template -->
	</xsl:template>

	<xsl:template name="bouclefor">
		<xsl:param name="min"></xsl:param>
		<xsl:param name="max"></xsl:param>
		<xsl:value-of select="$min"></xsl:value-of>
		<xsl:if test="number($min) &lt; number($max)">
			<xsl:call-template name="bouclefor">
				<xsl:with-param name="min">
					<xsl:value-of select="$min + 1"></xsl:value-of>
				</xsl:with-param>
				<xsl:with-param name="max">
					<xsl:value-of select="$max"></xsl:value-of>
				</xsl:with-param>
			</xsl:call-template>
			<fo:table-row>
				<fo:table-cell border="0.018cm solid #000000">
					<fo:block font-size="9pt">
						<xsl:text>&#160;</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border="0.018cm solid #000000">
					<fo:block font-size="9pt">
						<xsl:text>&#160;</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border="0.018cm solid #000000">
					<fo:block font-size="9pt">
						<xsl:text>&#160;</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border="0.018cm solid #000000">
					<fo:block font-size="9pt">
						<xsl:text>&#160;</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border="0.018cm solid #000000">
					<fo:block font-size="9pt">
						<xsl:text>&#160;</xsl:text>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border="0.018cm solid #000000">
					<fo:block font-size="9pt">
						<xsl:text>&#160;</xsl:text>
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
		</xsl:if>
	</xsl:template>

	<xsl:template name="basDePage">
		<fo:block text-align="center" font-size="6pt" line-height="10pt"
			border-top="#D1D7DC" border-top-style="solid" border-top-width="1pt"
			padding-top="2pt" padding-right="2pt" padding-left="2pt"
			padding-bottom="2pt">

			La loi 7817 du 6 janvier 1978 relative à
			l'informatique, aux
			fichiers et aux libertés s'applique à ce dossier.
			Elle vous donne le
			droit d'accès et de rectification sur les données
			vous concernant.
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