package com.servo.generateXML;

import ISPack.ISUtil.JPISException;
import com.servo.dms.SRVDMSImpl;
import com.servo.dms.SRVDMSInterface;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class SRVGenerateXML {

    static int intCriteriaCount = 0;

    public static StringBuffer isGetDocument(String strEngineName, String strVolIndex, String strClientSite, String strDocIndex, String strUserIndex) {
        System.out.println("====== INSIDE isGetDocument() =======");
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOISGetDocument_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOISGetDocument"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("VolumeIndex", strEngineName));
        inputXml.append(SRVUtil.writeTag("ClientSite", strEngineName));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserIndex", strEngineName));
        inputXml.append(SRVUtil.endTag("NGOISGetDocument_Input"));

        System.out.println("====== RETURNING inputXML from getAnnotations() =======");
        return inputXml;

    }

    public static StringBuffer getAnnotations(String strEngineName, String strSessionId, String docIndex, String pageNo, String prevAnnotIndex, String versionNo, String noOfRecordsToFetch, String sortOrder, String annotationType) {
        System.out.println("====== INSIDE getAnnotations() =======");
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetAnnotationGroupList_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetAnnotationGroupList"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", docIndex));
        inputXml.append(SRVUtil.writeTag("PageNo", pageNo));
        inputXml.append(SRVUtil.writeTag("PreviousAnnotationIndex", prevAnnotIndex));
        inputXml.append(SRVUtil.writeTag("VersionNo", versionNo));
        inputXml.append(SRVUtil.writeTag("SortOrder", sortOrder));
        inputXml.append(SRVUtil.writeTag("NoOfRecordsToFetch", noOfRecordsToFetch));
        inputXml.append(SRVUtil.writeTag("AnnotationType", annotationType));
        inputXml.append(SRVUtil.endTag("NGOGetAnnotationGroupList_Input"));

        System.out.println("====== RETURNING inputXML from getAnnotations() =======");
        return inputXml;
    }

    public static String setAnnotations(String strEngineName, String strSessionId,
            String documentIndex, String pageNo,
            String annotGroupList) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOSetAnnotations_Input"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", documentIndex));
        inputXml.append(SRVUtil.writeTag("PageNo", pageNo));
        inputXml.append(SRVUtil.writeTag("DeleteOtherGroups", "A"));
        inputXml.append(SRVUtil.writeTag("AnnotationGroups", annotGroupList));
        inputXml.append(SRVUtil.endTag("NGOSetAnnotations_Input"));

        System.out.println("====== RETURNING inputXML from setAnnotations() =======");
        return inputXml.toString();
    }

    public static String getGetAnnotationGroupPropertyXml(String strEngineName, String strUserDbId,
            String documentIndex, String pageNo, String annotIndex) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetAnnotationGroupProperty_Input"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strUserDbId));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", documentIndex));
        inputXml.append(SRVUtil.writeTag("PageNo", pageNo));
        inputXml.append(SRVUtil.writeTag("AnnotationIndex", annotIndex));
        inputXml.append(SRVUtil.endTag("NGOGetAnnotationGroupProperty_Input"));

        System.out.println("====== RETURNING inputXML from getGetAnnotationGroupPropertyXml() =======");
        return inputXml.toString();
    }

    public static String getGetDocPageBinDataXml(String strEngineName, String strUserDbId, String pageNo, String versionNo, String documentIndex) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetDocPageBinData_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetDocPageBinData"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strUserDbId));
        inputXml.append(SRVUtil.writeTag("PageNo", pageNo));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", documentIndex));
        inputXml.append(SRVUtil.writeTag("VersionNo", versionNo));
        inputXml.append(SRVUtil.endTag("NGOGetDocPageBinData_Input"));

        return inputXml.toString();
    }

    public static String getSetDocPageBinDataXml(String strEngineName, String strUserDbId, String pageNo, String documentIndex, String versionNo, String annotationData) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOSetDocPageBinData_Input"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strUserDbId));
        inputXml.append(SRVUtil.writeTag("PageNo", pageNo));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", documentIndex));
        inputXml.append(SRVUtil.writeTag("VersionNo", versionNo));
        inputXml.append(SRVUtil.writeTag("AnnotationData", annotationData));
        inputXml.append(SRVUtil.endTag("NGOSetDocPageBinData_Input"));

        return inputXml.toString();
    }

    public static StringBuffer getIdForName(String strEngineName, String strSessionId, String strFolderName, String strParentFolderIndex) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.genXMLTag());
        inputXml.append(SRVUtil.startTag("NGOGetIdFromName_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetIdFromName"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("ObjectType", "F"));
        inputXml.append(SRVUtil.writeTag("ObjectName", strFolderName));
        inputXml.append(SRVUtil.writeTag("Index", strParentFolderIndex));
        inputXml.append(SRVUtil.endTag("NGOGetIdFromName_Input"));
        return inputXml;
    }

    public static String AddDocument(String strEngineName, String strUserDBId,
            String strGroupId, String strParentFolderIndex,
            String strNoOfPages, String strAccessType, String strDocumentName,
            String strCreatedDateTime, String strExpiryDateTime,
            String strVersionFlag, String strDocumentType,
            String strDocumentSize, String strCreatedByApp,
            String strCreatedByAppName, String strIsIndex,
            String strTextIsIndex, String strODMADocumentIndex,
            String strComment, String strDocumentAuthor,
            String strOwnerIndex, String strEnableLog,
            String strFTSFlag, String strDataDefXml,
            String strKeywords, String strVolumeIndex, String strFilePath
    ) throws JPISException, Exception {

        StringBuffer inputXml = new StringBuffer(100);

        inputXml.append(SRVUtil.startTag("NGOAddDocument_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOAddDocument"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strUserDBId));
        inputXml.append(SRVUtil.writeTag("GroupIndex", strGroupId));
        inputXml.append(SRVUtil.writeTag("ParentFolderIndex", strParentFolderIndex));
        inputXml.append(SRVUtil.writeTag("NoOfPages", strNoOfPages));
        inputXml.append(SRVUtil.writeTag("AccessType", strAccessType));
        inputXml.append(SRVUtil.writeTag("DocumentName", strDocumentName));
        inputXml.append(SRVUtil.writeTag("CreationDateTime", strCreatedDateTime));
        inputXml.append(SRVUtil.writeTag("ExpiryDateTime", strExpiryDateTime));
        inputXml.append(SRVUtil.writeTag("VersionFlag", strVersionFlag));
        inputXml.append(SRVUtil.writeTag("DocumentType", strDocumentType));
        inputXml.append(SRVUtil.writeTag("DocumentSize", strDocumentSize));
        inputXml.append(SRVUtil.writeTag("CreatedByApp", strCreatedByApp));
        inputXml.append(SRVUtil.writeTag("CreatedByAppName", strCreatedByAppName));
        inputXml.append(SRVUtil.writeTag("ISIndex", strIsIndex));
        //inputXml.append(SRVUtil.startTag("TextISIndex", strTextImageIndex);
        inputXml.append(SRVUtil.writeTag("ODMADocumentIndex", strODMADocumentIndex));
        inputXml.append(SRVUtil.writeTag("Comment", strComment));
        inputXml.append(SRVUtil.writeTag("Author", strDocumentAuthor));
        inputXml.append(SRVUtil.writeTag("OwnerIndex", strOwnerIndex));
        inputXml.append(SRVUtil.writeTag("EnableLog", strEnableLog));
        inputXml.append(SRVUtil.writeTag("FTSFlag", strFTSFlag));
        inputXml.append(SRVUtil.writeTag("Keywords", strKeywords));
        inputXml.append(SRVUtil.writeTag("VolumeIndex", strVolumeIndex));
        inputXml.append(SRVUtil.writeTag("FilePath", strFilePath));
        inputXml.append(SRVUtil.writeTag("DataDefinition", strDataDefXml));
        inputXml.append(SRVUtil.endTag("NGOAddDocument_Input"));

        System.out.println("INput XML --- > " + inputXml);
        SRVUtil.printOut("[SRVGenerateXML] [addDocument]Input XML --- > " + inputXml);

        SRVDMSInterface dms = (SRVDMSInterface) new SRVDMSImpl();
        String out = dms.addDocument(inputXml.toString());
        SRVUtil.printOut("[SRVGenerateXML] [addDocument]Output XML --- > " + out);

        return out;
    }

    public static String getFolderProperty(String strEngineName, String strSessionId,
            String strFolderIndex, String strDataAlsoFlag) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetFolderProperty_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetFolderProperty"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("FolderIndex", strFolderIndex));
        inputXml.append(SRVUtil.writeTag("DataAlsoFlag", strDataAlsoFlag));
        inputXml.append(SRVUtil.endTag("NGOGetFolderProperty_Input"));

        return inputXml.toString();

    }

    public static String getDataDefIdForName(String strEngineName, String strSessionId,
            String strDataDefName) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetDataDefIdForName_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetDataDefIdForName"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("DataDefName", strDataDefName));
        inputXml.append(SRVUtil.endTag("NGOGetDataDefIdForName_Input"));

        return inputXml.toString();

    }

    /**
     * ******************************************************************
     * Function name : NGOGetDataDefProperty Description : Return type : public
     * String Argument 1 : String cabinetName Argument 2 : String userDbId
     * Argument 3 : String dataDefIndex
     * ******************************************************************
     */
    public static String getDataDefProperty(String strEngineName, String strSessionId,
            String dataDefIndex) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetDataDefProperty_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetDataDefProperty"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("DataDefIndex", dataDefIndex));
        inputXml.append(SRVUtil.endTag("NGOGetDataDefProperty_Input"));

        return inputXml.toString();
    }

    public static String getDataDefXML(String strDataDefIndex, Iterator itr, String ApsId, String applicantType, String parentDocument, String childDocument, String applicationFormNo) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("DataDefinition"));
        inputXml.append(SRVUtil.writeTag("DataDefIndex", strDataDefIndex));
        inputXml.append(SRVUtil.startTag("Fields"));

        while (itr.hasNext()) {
            Map.Entry pairs = (Map.Entry) itr.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            String[] index = pairs.getValue().toString().split("#");

            if (pairs.getKey().toString().equalsIgnoreCase("ParentType")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], parentDocument));
            } else if (pairs.getKey().toString().equalsIgnoreCase("ChildName")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], childDocument));
            } else if (pairs.getKey().toString().equalsIgnoreCase("ApplicationFormNo")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], applicationFormNo));
            } else if (pairs.getKey().toString().equalsIgnoreCase("APSID")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], ApsId));
            }
            //itr.remove(); // avoids a ConcurrentModificationException
        }

        inputXml.append(SRVUtil.endTag("Fields"));
        inputXml.append(SRVUtil.endTag("DataDefinition"));

        System.out.println("getDataDefXML () ----------------- > " + inputXml);
        return inputXml.toString();
    }

    public static String getDataDefFieldXML(String strIndexId, String strIndexType, String indexValue) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("Field"));
        inputXml.append(SRVUtil.writeTag("IndexId", strIndexId));
        inputXml.append(SRVUtil.writeTag("IndexType", strIndexType));
        inputXml.append(SRVUtil.writeTag("IndexValue", indexValue));
        inputXml.append(SRVUtil.endTag("Field"));

        return inputXml.toString();
    }

    public static String searchDocumentExt(String strEngineName, String strSessionId,
            String lookInFolderList, String cAddSubFolderFlag,
            String strName, String strOwner,
            String searchText,
            String cVersionFlag,
            String strCreatedDateTimeCriteria, String strExpiryDateTimeCriteria,
            String strAccessDateTimeCriteria, String strRevisedDateTimeCriteria,
            //String dataDefIdList, String dataDefCriterion,
            String nSearchScope, String strPrevFolderList,
            String searchOnAlias, String keywordList,
            String globIdCriteriaList, String referenceFlag,
            String sortOrder, String groupIndex,
            String startFrom, String noOfRecordsToFetch,
            String nOrderBy, String cDocumentType,
            //String strDocumentSizeCriteria, 
            //String strNoOfPagesCriteria,
            String strCreatedByAppName, String strCheckOutStatus,
            String totalhitcount,
            String strCheckOutBy,
            //String objectTypelist,
            String dataAlsoFlag,
            String createdbyappname,
            String strAuthor, String strAnnotationFlag,
            String strLinkDocFlag,
            String strPreviousDocIndex,
            String strIncludeSystemFolder,
            String strReportFlag // APSId and Application Form No for
            , String strApsId, String strApplicationFormNo, Iterator itr, String dataDefIndex) {

        StringBuffer inputXml = new StringBuffer(100);

        //int intCriteriaCount = 0;
        inputXml.append(SRVUtil.startTag("NGOSearchDocumentExt_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOSearchDocumentExt"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));

        inputXml.append(SRVUtil.writeTag("SearchText", searchText));

        inputXml.append(SRVUtil.writeTag("SearchOnPreviousVersions", cVersionFlag));
        inputXml.append(SRVUtil.writeTag("LookInFolder", lookInFolderList));
        inputXml.append(SRVUtil.writeTag("IncludeSubFolder", cAddSubFolderFlag));
        inputXml.append(SRVUtil.writeTag("Name", strName));
        inputXml.append(SRVUtil.writeTag("Owner", strOwner));
        inputXml.append(SRVUtil.writeTag("CreationDateRange", strCreatedDateTimeCriteria));
        inputXml.append(SRVUtil.writeTag("ExpiryDateRange", strExpiryDateTimeCriteria));
        inputXml.append(SRVUtil.writeTag("AccessedDateRange", strAccessDateTimeCriteria));
        inputXml.append(SRVUtil.writeTag("RevisedDateRange", strRevisedDateTimeCriteria));
        //inputXml.append(SRVUtil.writeTag("DataDefinitions", dataDefIdList));

        inputXml.append(SRVUtil.startTag("DataDefCriterion"));

        while (itr.hasNext()) {
            Map.Entry pairs = (Map.Entry) itr.next();
            System.out.println("searchDocumentExt ---------------- > " + pairs.getKey() + " = " + pairs.getValue());
            String[] dataDefProperty = pairs.getValue().toString().split("#");
            if (pairs.getKey().toString().equalsIgnoreCase("ApsId") || pairs.getKey().toString().equalsIgnoreCase("ApplicationFormNo")) {
                inputXml.append(createDataDefCriterion(pairs.getKey().toString(), dataDefProperty[0], strApsId, strApplicationFormNo, dataDefIndex));
            }

//                else if(pairs.getKey().toString().equalsIgnoreCase("ApplicationFormNo"))
//                {
//                    inputXml.append(getDataDefFieldXML(index[0], index[1], childDocument));
//                }
            //inputXml.append(createDataDefCriterion(ApsId, ApplicationFormNo, itr));
        }

        intCriteriaCount = 0;

        inputXml.append(SRVUtil.endTag("DataDefCriterion"));

        inputXml.append(SRVUtil.writeTag("SearchScope", nSearchScope));
        inputXml.append(SRVUtil.writeTag("PreviousList", strPrevFolderList));
        inputXml.append(SRVUtil.writeTag("SearchOnAlias", searchOnAlias));
        inputXml.append(SRVUtil.writeTag("Keywords", keywordList));
        inputXml.append(SRVUtil.writeTag("GlobalIndexCriterion", globIdCriteriaList));
        inputXml.append(SRVUtil.writeTag("ReferenceFlag", referenceFlag));
        inputXml.append(SRVUtil.writeTag("SortOrder", sortOrder));
        inputXml.append(SRVUtil.writeTag("GroupIndex", groupIndex));
        inputXml.append(SRVUtil.writeTag("StartFrom", startFrom));
        inputXml.append(SRVUtil.writeTag("NoOfRecordsToFetch", noOfRecordsToFetch));
        inputXml.append(SRVUtil.writeTag("OrderBy", nOrderBy));
        //inputXml.append(SRVUtil.writeTag("DocumentType", cDocumentType));
        //inputXml.append(SRVUtil.writeTag("DocumentSizeCriteria", strDocumentSizeCriteria));
        //inputXml.append(SRVUtil.writeTag("NoOfPagesCriteria", strNoOfPagesCriteria));
//    inputXml.append(SRVUtil.writeTag("CreatedByAppName", strCreatedByAppName));
        inputXml.append(SRVUtil.writeTag("CheckOutStatus", strCheckOutStatus));
        inputXml.append(SRVUtil.writeTag("MaximumHitCountFlag", totalhitcount));
        inputXml.append(SRVUtil.writeTag("CheckOutByUser", strCheckOutBy));

        inputXml.append(createObjectTypes());

        inputXml.append(SRVUtil.writeTag("DataAlsoFlag", dataAlsoFlag));
        inputXml.append(SRVUtil.writeTag("CreatedByAppName", createdbyappname));
        inputXml.append(SRVUtil.writeTag("Author", strAuthor));
        inputXml.append(SRVUtil.writeTag("AnnotationFlag", strAnnotationFlag));

        inputXml.append(SRVUtil.writeTag("LinkDocFlag", strLinkDocFlag));
        inputXml.append(SRVUtil.writeTag("PrevDocIndex", strPreviousDocIndex));
        inputXml.append(SRVUtil.writeTag("IncludeSystemFolder", strIncludeSystemFolder));
        inputXml.append(SRVUtil.writeTag("ReportFlag", strReportFlag));

        inputXml.append(SRVUtil.endTag("NGOSearchDocumentExt_Input"));

        return inputXml.toString();
    }

    public static String searchFolder(String strEngineName, String strSessionId,
            String lookInFolderList, String cAddSubFolderFlag,
            String strName, String strOwner,
            String strCreatedDateTimeCriteria, String strExpiryDateTimeCriteria,
            String strAccessDateTimeCriteria, String strRevisedDateTimeCriteria,
            String nSearchScope, String strPrevFolderList,
            String referenceFlag, String startFrom,
            String noOfRecordsToFetch, String nOrderBy, String sortOrder,
            String maxCount, String strFolderType,
            String strIncludeTrashFlag, String strReportFlag,
            String strShowPath, String strApsId, String strApplicationFormNo, Iterator itr, String dataDefIndex) {

        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOSearchFolder_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOSearchFolder"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("LookInFolder", lookInFolderList));
        inputXml.append(SRVUtil.writeTag("IncludeSubFolder", cAddSubFolderFlag));
        inputXml.append(SRVUtil.writeTag("Name", strName));
        inputXml.append(SRVUtil.writeTag("Owner", strOwner));
        inputXml.append(SRVUtil.writeTag("CreationDateRange", strCreatedDateTimeCriteria));
        inputXml.append(SRVUtil.writeTag("ExpiryDateRange", strExpiryDateTimeCriteria));
        inputXml.append(SRVUtil.writeTag("AccessDateRange", strAccessDateTimeCriteria));
        inputXml.append(SRVUtil.writeTag("RevisedDateRange", strRevisedDateTimeCriteria));
        //inputXml.append(SRVUtil.writeTag("DataDefinitions", dataDefIdList));
        inputXml.append(SRVUtil.startTag("DataDefCriterion"));

        while (itr.hasNext()) {
            Map.Entry pairs = (Map.Entry) itr.next();
            System.out.println("searchDocumentExt ---------------- > " + pairs.getKey() + " = " + pairs.getValue());
            String[] dataDefProperty = pairs.getValue().toString().split("#");
            if (pairs.getKey().toString().equalsIgnoreCase("ApsId") || pairs.getKey().toString().equalsIgnoreCase("ApplicationFormNo")) {
                inputXml.append(createDataDefCriterion(pairs.getKey().toString(), dataDefProperty[0], strApsId, strApplicationFormNo, dataDefIndex));
            }
        }

        intCriteriaCount = 0;

        inputXml.append(SRVUtil.endTag("DataDefCriterion"));
        inputXml.append(SRVUtil.writeTag("SearchScope", nSearchScope));
        inputXml.append(SRVUtil.writeTag("PrevFolderList", strPrevFolderList));
        inputXml.append(SRVUtil.writeTag("ReferenceFlag", referenceFlag));
        inputXml.append(SRVUtil.writeTag("SortOrder", sortOrder));
        inputXml.append(SRVUtil.writeTag("StartFrom", startFrom));
        inputXml.append(SRVUtil.writeTag("NoOfRecordsToFetch", noOfRecordsToFetch));
        inputXml.append(SRVUtil.writeTag("OrderBy", nOrderBy));
        inputXml.append(SRVUtil.writeTag("MaximumHitCountFlag", maxCount));
        inputXml.append(SRVUtil.writeTag("FolderType", strFolderType));
        inputXml.append(SRVUtil.writeTag("IncludeTrashFlag", strIncludeTrashFlag));
        inputXml.append(SRVUtil.writeTag("ReportFlag", strReportFlag));
        inputXml.append(SRVUtil.writeTag("ShowPath", strShowPath));
        /*<IncludeTrashFlag>N</IncludeTrashFlag>
         <ReportFlag>N</ReportFlag>
         <ShowPath>Y</ShowPath>*/

        inputXml.append(SRVUtil.endTag("NGOSearchFolder_Input"));

        return inputXml.toString();

    }

    public static String createDataDefCriterion(String strPropertyName, String strIndexId, String strApsId, String strApplicationFormNo, String dataDefIndex) {
        System.out.println("intCriteriaCount --------------- > " + intCriteriaCount);
        StringBuffer inputXml = new StringBuffer(100);
        String joinCondition = "";
        if (strApsId != null && !strApsId.equalsIgnoreCase("") && strApplicationFormNo != null && !strApplicationFormNo.equalsIgnoreCase("")) {
            if (intCriteriaCount == 0) {
                System.out.println("intCriteriaCount == 0 --------and--------- joinCondition = AND");
                joinCondition = "AND";
            } else {
                System.out.println("intCriteriaCount != 0 --------and--------- joinCondition = ");
                joinCondition = "";
            }
        }
        if (strPropertyName.equalsIgnoreCase("ApsId") && strApsId != null && !strApsId.equalsIgnoreCase("")) {
            inputXml.append(SRVUtil.startTag("DataDefCriteria"));
            inputXml.append(SRVUtil.writeTag("DataDefIndex", dataDefIndex));
            inputXml.append(SRVUtil.writeTag("IndexId", strIndexId));
            inputXml.append(SRVUtil.writeTag("Operator", "="));
            inputXml.append(SRVUtil.writeTag("IndexValue", strApsId));

            inputXml.append(SRVUtil.writeTag("JoinCondition", joinCondition));

            inputXml.append(SRVUtil.endTag("DataDefCriteria"));
        } else if (strPropertyName.equalsIgnoreCase("ApplicationFormNo") && strApplicationFormNo != null && !strApplicationFormNo.equalsIgnoreCase("")) {
            inputXml.append(SRVUtil.startTag("DataDefCriteria"));
            inputXml.append(SRVUtil.writeTag("DataDefIndex", dataDefIndex));
            inputXml.append(SRVUtil.writeTag("IndexId", strIndexId));
            inputXml.append(SRVUtil.writeTag("Operator", "="));
            inputXml.append(SRVUtil.writeTag("IndexValue", strApplicationFormNo));
            inputXml.append(SRVUtil.writeTag("JoinCondition", joinCondition));
            inputXml.append(SRVUtil.endTag("DataDefCriteria"));
        }

        intCriteriaCount = intCriteriaCount + 1;

        return inputXml.toString();
    }

    public static String getDocumentList(String strEngineName, String strUserDBId,
            String strCreationDateTime, String strFolderIndex,
            String strDocIndex, String strPrevIndex,
            String strLastSortField, String strStartFrom,
            String strNoOfRecordsToFetch, String strOrderBy,
            String strSortOrder, String strDataAlsoFlag,
            String strLinkDocFlag, String strAnnotationFlag,
            String strPrevRefIndex, String strLastRefField,
            String strRefOrderBy, String strRefSortOrder,
            String strNoOfRefToFetch, String strDocumentType,
            String strRecursiveFlag, String strThumbnailAlsoFlag) {

        StringBuffer inputXml = new StringBuffer(100);

        inputXml.append(SRVUtil.startTag("NGOGetDocumentListExt_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetDocumentListExt"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strUserDBId));
        inputXml.append(SRVUtil.writeTag("CurrentDateTime", strCreationDateTime));
        inputXml.append(SRVUtil.writeTag("FolderIndex", strFolderIndex));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", strDocIndex));
        inputXml.append(SRVUtil.writeTag("PreviousIndex", strPrevIndex));
        inputXml.append(SRVUtil.writeTag("LastSortField", strLastSortField));
        inputXml.append(SRVUtil.writeTag("StartPos", strStartFrom));
        inputXml.append(SRVUtil.writeTag("NoOfRecordsToFetch", strNoOfRecordsToFetch));
        inputXml.append(SRVUtil.writeTag("OrderBy", strOrderBy));
        inputXml.append(SRVUtil.writeTag("SortOrder", strSortOrder));
        inputXml.append(SRVUtil.writeTag("DataAlsoFlag", strDataAlsoFlag));
        inputXml.append(SRVUtil.writeTag("AnnotationFlag", strAnnotationFlag));
        inputXml.append(SRVUtil.writeTag("LinkDocFlag", strLinkDocFlag));
        inputXml.append(SRVUtil.writeTag("PreviousRefIndex", strPrevRefIndex));
        inputXml.append(SRVUtil.writeTag("LastRefField", strLastRefField));
        inputXml.append(SRVUtil.writeTag("RefOrderBy", strRefOrderBy));
        inputXml.append(SRVUtil.writeTag("RefSortOrder", strRefSortOrder));
        inputXml.append(SRVUtil.writeTag("NoOfReferenceToFetch", strNoOfRefToFetch));
        inputXml.append(SRVUtil.writeTag("DocumentType", strDocumentType));
        //// recurssive flag added....
        inputXml.append(SRVUtil.writeTag("RecursiveFlag", strRecursiveFlag));
        inputXml.append(SRVUtil.writeTag("ThumbnailAlsoFlag", strThumbnailAlsoFlag));
        inputXml.append(SRVUtil.endTag("NGOGetDocumentListExt_Input"));

        return inputXml.toString();

    }

    public static String createObjectTypes() {
        StringBuffer inputXml = new StringBuffer(100);

        inputXml.append(SRVUtil.startTag("ObjectTypes"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "1"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "2"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "8"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "11"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "13"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "14"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "15"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "16"));
        inputXml.append(SRVUtil.writeTag("ObjectType", "17"));
        inputXml.append(SRVUtil.endTag("ObjectTypes"));

        return inputXml.toString();
    }

    public static String getDocumentProperty(String strEngineName, String strSessionId,
            String strCurrentDateTime, String strDocumentIndex, String strParentFolderIndex, String strVersionNo, String strDataAlsoFlag, String strThumbNailAlsoFlag) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetDocumentProperty_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetDocumentProperty"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("CurrentDateTime", strCurrentDateTime));
        inputXml.append(SRVUtil.writeTag("DocumentIndex", strDocumentIndex));
        inputXml.append(SRVUtil.writeTag("ParentFolderIndex", strParentFolderIndex));
        inputXml.append(SRVUtil.writeTag("VersionNo", strVersionNo));
        inputXml.append(SRVUtil.writeTag("DataAlsoFlag", strDataAlsoFlag));
        inputXml.append(SRVUtil.writeTag("ThumbNailAlsoFlag", strThumbNailAlsoFlag));
        inputXml.append(SRVUtil.endTag("NGOGetDocumentProperty_Input"));

        return inputXml.toString();
    }

//  For siteId
    public static StringBuffer getVolumeAttributes(String engineName, String volumeIndex) {

        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOISGetVolumeAttributes"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOISGetVolumeAttributes"));
        inputXml.append(SRVUtil.writeTag("CabinetName", engineName));
        inputXml.append(SRVUtil.writeTag("VolumeIndex", volumeIndex));
        inputXml.append(SRVUtil.endTag("NGOISGetVolumeAttributes"));

        return inputXml;
    }

//  create/add new folder
    public static StringBuffer addFolder(String strEngineName, String strSessionId,
            String strFolderName, String strParentFolderId,
            String strCreationDateTime, String strAccessType,
            String strImageVolumeIndex, String strFolderType,
            String strLocation, String strComment,
            String strEnableFTSFlag, String strNoOfDocuments,
            String strNoOfSubFolders, String strDataDefXml
    ) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOAddFolder_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOAddFolder"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.startTag("Folder"));
        inputXml.append(SRVUtil.writeTag("ParentFolderIndex", strParentFolderId));
        inputXml.append(SRVUtil.writeTag("FolderName", strFolderName));
        //to avoid duplicate folder creation begin
        inputXml.append(SRVUtil.writeTag("DuplicateName", "N"));
        //to avoid duplicate folder creation end
        inputXml.append(SRVUtil.writeTag("CreationDateTime", strCreationDateTime));

//        try {
//            inputXml.append(SRVUtil.writeTag("AccessType",WFConfiguration.getConfValue("WIFolderAccessType", "I")));
//        } catch (WFException ex) {
//           // ex.printStackTrace();
//        }
        try {
            inputXml.append(SRVUtil.writeTag("AccessType", strAccessType));
        } catch (Exception ex) {
            // ex.printStackTrace();
        }
        inputXml.append(SRVUtil.writeTag("ImageVolumeIndex", strImageVolumeIndex));
        inputXml.append(SRVUtil.writeTag("FolderType", strFolderType));
        inputXml.append(SRVUtil.writeTag("Location", strLocation));
        inputXml.append(SRVUtil.writeTag("Comment", strComment));
        inputXml.append(SRVUtil.writeTag("EnableFTSFlag", strEnableFTSFlag));
        inputXml.append(SRVUtil.writeTag("NoOfDocuments", strNoOfDocuments));
        inputXml.append(SRVUtil.writeTag("NoOfSubFolders", strNoOfSubFolders));
        inputXml.append(SRVUtil.writeTag("DataDefinition", strDataDefXml));
        inputXml.append(SRVUtil.endTag("Folder"));
        inputXml.append(SRVUtil.endTag("NGOAddFolder_Input"));

        return inputXml;

    }

    // Get DataDef( DataClass ) list 
    public static StringBuffer getDataDefList(String strEngineName, String strSessionId,
            String orderBy, String sortOrder,
            String prevIndex, String lastSortField,
            String noOfRecordsToFetch, String type,
            String groupId) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOGetDataDefListExt_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOGetDataDefListExt"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));
        inputXml.append(SRVUtil.writeTag("OrderBy", orderBy));
        inputXml.append(SRVUtil.writeTag("SortOrder", sortOrder));
        inputXml.append(SRVUtil.writeTag("PreviousIndex", prevIndex));
        inputXml.append(SRVUtil.writeTag("LastSortField", lastSortField));
        inputXml.append(SRVUtil.writeTag("NoOfRecordsToFetch", noOfRecordsToFetch));
        inputXml.append(SRVUtil.writeTag("Type", type));
        inputXml.append(SRVUtil.writeTag("GroupId", groupId));

        inputXml.append(SRVUtil.endTag("NGOGetDataDefListExt_Input"));

//    DMSInterface dms = (DMSInterface)new DMSImpl();
//    String outXml = dms.getDataDefList(wfInputXml.toString(),httpSession);
        return inputXml;
    }

    public static StringBuffer changeFolderProperty(String strEngineName, String strSessionId,
            String nFolderIndex,
            String nNameLength,
            String cVersionFlag, String strComment,
            String strDataDefIndex,
            Iterator itr, String strApplicationFormNo, String strAPSId
    ) {
        StringBuffer inputXml = new StringBuffer(100);
        inputXml.append(SRVUtil.startTag("NGOChangeFolderProperty_Input"));
        inputXml.append(SRVUtil.writeTag("Option", "NGOChangeFolderProperty"));
        inputXml.append(SRVUtil.writeTag("CabinetName", strEngineName));
        inputXml.append(SRVUtil.writeTag("UserDBId", strSessionId));

        inputXml.append(SRVUtil.startTag("Folder"));
        inputXml.append(SRVUtil.writeTag("FolderIndex", nFolderIndex));
        //inputXml.append(SRVUtil.writeTag("FolderName", strFolderName));
        inputXml.append(SRVUtil.writeTag("NameLength", nNameLength));

        inputXml.append(SRVUtil.writeTag("VersionFlag", cVersionFlag));
        inputXml.append(SRVUtil.writeTag("Comment", strComment));
        //inputXml.append(SRVUtil.writeTag("FinalizedFlag", cFinalizedFlag));
        //inputXml.append(SRVUtil.writeTag("Owner", owner));
        inputXml.append(SRVUtil.startTag("DataDefinition"));

        inputXml.append(SRVUtil.writeTag("DataDefIndex", strDataDefIndex));
        inputXml.append(SRVUtil.startTag("Fields"));

        while (itr.hasNext()) {
            Map.Entry pairs = (Map.Entry) itr.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            String[] index = pairs.getValue().toString().split("#");

            if (pairs.getKey().toString().equalsIgnoreCase("ParentType")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], ""));
            } else if (pairs.getKey().toString().equalsIgnoreCase("ChildName")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], ""));
            } else if (pairs.getKey().toString().equalsIgnoreCase("ApplicationFormNo")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], strApplicationFormNo));
            } else if (pairs.getKey().toString().equalsIgnoreCase("APSID")) {
                inputXml.append(getDataDefFieldXML(index[0], index[1], strAPSId));
            }
            //itr.remove(); // avoids a ConcurrentModificationException
        }
        inputXml.append(SRVUtil.endTag("Fields"));
        inputXml.append(SRVUtil.endTag("Folder"));

        inputXml.append(SRVUtil.endTag("NGOChangeFolderProperty_Input"));

//    DMSInterface dms = (DMSInterface)new DMSImpl();
//    String outXml = dms.changeFolderProperty(wfInputXml.toString(),httpSession);
        return inputXml;
    }

    public static String getAnnotationData(String inputXML) throws XMLStreamException {
        String midCon = inputXML.substring(inputXML.lastIndexOf("</") + 2, inputXML.lastIndexOf("_Output>"));
        inputXML = inputXML.replaceAll(midCon + "_Output>", "NGOGetAnnotationGroupList_Output>");

//        System.out.println("inputXML " + inputXML);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(inputXML));

        String annotGroupName = null;
        StringBuilder annotationBuffer = new StringBuilder();
        String localNameTmp = null;

        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    localNameTmp = reader.getLocalName();
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if ("AnnotationBuffer".equals(localNameTmp)) {
                        annotationBuffer.append(reader.getText());
                    }
                    if ("AnnotGroupName".equals(localNameTmp)) {
                        annotGroupName = reader.getText();
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    localNameTmp = null;
                    break;

            }

        }

        StringBuilder output = new StringBuilder();

        output.append("[AnnotationGroupHeader]\n" + "TotalGroups=1\n" + "[Group1]\n" + "Name=")
                .append(annotGroupName).append("\n" + "[").append(annotGroupName)
                .append("AnnotationHeader]\n").append(annotationBuffer.toString());

        System.out.println("getAnnotationData output : " + output);
        System.out.println("getAnnotationData annotGroupName : " + annotGroupName);
        System.out.println("getAnnotationData annotationBuffer : " + annotationBuffer);

        SRVUtil.printOut("getAnnotationData output : " + output);
        SRVUtil.printOut("getAnnotationData annotGroupName : " + annotGroupName);
        SRVUtil.printOut("getAnnotationData annotationBuffer : " + annotationBuffer);

//        return output.toString().replaceAll("FileName=(\\d*)", "FileName=1");
        return output.toString();
    }

}
