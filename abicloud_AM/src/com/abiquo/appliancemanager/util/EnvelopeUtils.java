package com.abiquo.appliancemanager.util;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.dmtf.schemas.ovf.envelope._1.DiskSectionType;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
import org.dmtf.schemas.ovf.envelope._1.SectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualDiskDescType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemType;

import com.abiquo.appliancemanager.exceptions.IdAlreadyExists;
import com.abiquo.appliancemanager.exceptions.IdNotFound;

/**
 * Manipulate objects on the OVF-envelope name space. Hide from other classes the use of JAXB.
 */
public class EnvelopeUtils
{
    /** Generated factory to create XML OVF-elements in OVF name space . */
    private final static org.dmtf.schemas.ovf.envelope._1.ObjectFactory envelopFactory =
        new org.dmtf.schemas.ovf.envelope._1.ObjectFactory();

    /**
     * Adds a VirtualDiskDescription to an existing VirtualSystem.
     * 
     * @throws IdAlreadyExists if the provided VirtualDiscDescription id is already on the
     *             VirutalSystem's DiskSection.
     */
    public static void addDisk(VirtualSystemType vs, VirtualDiskDescType vDisk)
        throws IdAlreadyExists
    {
        DiskSectionType sectionDisk = null;
        boolean isSectionDiskPresent = false;

        for (JAXBElement< ? extends SectionType> section : vs.getSection())
        {
            if (section.getValue() instanceof DiskSectionType)
            {
                sectionDisk = (DiskSectionType) section.getValue();
                isSectionDiskPresent = true;
            }
        }

        if (!isSectionDiskPresent)
        {
            sectionDisk = envelopFactory.createDiskSectionType();
            vs.getSection().add(envelopFactory.createDiskSection(sectionDisk));
        }

        for (VirtualDiskDescType vdd : sectionDisk.getDisk())
        {
            if (vDisk.getDiskId().equalsIgnoreCase(vdd.getDiskId()))
            {
                final String msg =
                    "The VirtualDiskDescription diskId" + vDisk.getDiskId()
                        + " already on DiskSection for VirtualSystem " + vs.getId();
                throw new IdAlreadyExists(msg);
            }
        }
        sectionDisk.getDisk().add(vDisk);
    }

    /**
     * Crates and adds a VirtualDiskDescription to an existing VirtualSystem.
     * 
     * @throws IdAlreadyExists if the provided VirtualDiscDescription id is already on the
     *             VirutalSystem's DiskSection.
     */
    public static void addDisk(VirtualSystemType vs, String capacity, String capacityUnits,
        String diskId, String fileRef, String format, String parentRef, Long populatedSize)
        throws IdAlreadyExists
    {
        VirtualDiskDescType vDisk;

        vDisk = envelopFactory.createVirtualDiskDescType();
        vDisk.setCapacity(capacity);
        vDisk.setCapacityAllocationUnits(capacityUnits);
        vDisk.setDiskId(diskId);
        vDisk.setFileRef(fileRef);
        vDisk.setFormat(format);
        vDisk.setParentRef(parentRef);
        vDisk.setPopulatedSize(populatedSize);

        addDisk(vs, vDisk);
    }

    /**
     * Adds a File to an existing VirtualSystem's Envelope.
     * 
     * @throws IdAlreadyExists if the provided File id is already on the Envelope's
     *             ReferencesSection.
     */
    public static void addFile(EnvelopeType envelop, FileType fileRef) throws IdAlreadyExists
    {

        for (FileType file : envelop.getReferences().getFile())
        {
            if (file.getId().equalsIgnoreCase(fileRef.getId()))
            {
                final String msg =
                    "The File fileId" + fileRef.getId() + " already on ReferencesSection";
                throw new IdAlreadyExists(msg);
            }
        }

        envelop.getReferences().getFile().add(fileRef);
    }

    /**
     * Creates and adds a File to an existing VirtualSystem's Envelope.
     * 
     * @throws IdAlreadyExists if the provided File id is already on the Envelope's
     *             ReferencesSection.
     */
    public static void addFile(EnvelopeType envelop, BigInteger fileSize, Long chunkSize,
        String compresion, String href, String fileId) throws IdAlreadyExists
    {
        FileType fileRef;

        fileRef = envelopFactory.createFileType();

        fileRef.setSize(fileSize);
        fileRef.setChunkSize(chunkSize);
        fileRef.setCompression(compresion);
        fileRef.setHref(href);
        fileRef.setId(fileId);

        addFile(envelop, fileRef);
    }

    /**
     * Envelope TypeFactory. TODO create an specific class. Creates a FileType form the OVF envelope
     * schema . TODO indicate mandatory parameters (witch can be null)
     */
    public static FileType createFileType(BigInteger fileSize, Long chunkSize, String compresion,
        String href, String fileId)
    {
        FileType fileRef;

        fileRef = envelopFactory.createFileType();

        fileRef.setSize(fileSize);
        fileRef.setHref(href);
        fileRef.setId(fileId);

        if (chunkSize != null)
        {
            fileRef.setChunkSize(chunkSize);
        }
        if (compresion != null)
        {
            fileRef.setCompression(compresion);
        }

        return fileRef;
    }

    /**
     * Change the location (FileType.hRef) of an existing file on the Envelope's ReferenceSection.
     * It assumes the file has the same size, and other attributes (TODO).
     * 
     * @param envelop, the target VirtualSystem's envelope.
     * @param fileId, an existing FileType id on the envelope ReferenceSection.
     * @param newHRef, the new location for this file.
     * @throws IdNotFound if fileId is not on the Envelope's ReferenceSection.
     */
    public static void changeFileReference(EnvelopeType envelop, String fileId, String newHRef)
        throws IdNotFound
    {
        boolean found = false;
        for (FileType file : envelop.getReferences().getFile())
        {
            if (fileId.equals(file.getId()))
            {
                found = true;
                file.setHref(newHRef);
            }
        }

        if (!found)
        {
            final String msg = "The file id " + fileId + " not present on ReferencesSection";
            throw new IdNotFound(msg);
        }
    }

    /**
     * Gets all the referenced files on an envelope.
     * 
     * @param envelop, the target VirtualSystem's envelope.
     * @return all the file locations on the ReferencesSection for the given envelope.
     */
    public static List<String> getFileReferences(EnvelopeType envelop)
    {
        List<String> fileRefs = new LinkedList<String>();

        for (FileType files : envelop.getReferences().getFile())
        {
            fileRefs.add(files.getHref());
        }

        return fileRefs;
    }

}
