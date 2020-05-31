package ru.mcx73.gis.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mcx73.gis.entity.Docs;
import ru.mcx73.gis.repository.DocsRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class FileService implements DocsRepository {
    @Autowired
    private DocsRepository docsRepository;

    public boolean deleteFile(Long fileId) {
        if (docsRepository.findById(fileId).isPresent()) {
            docsRepository.deleteById(fileId);
            return true;
        }
        return false;
    }

    @Override
    public <S extends Docs> S save(S s) {
        return null;
    }

    @Override
    public <S extends Docs> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Docs> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Docs> findAll() {
        return null;
    }

    @Override
    public Iterable<Docs> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Docs docs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Docs> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    private static final Map<String, String> letters = new HashMap<String, String>();
    static {
        letters.put("А", "A");
        letters.put("Б", "B");
        letters.put("В", "V");
        letters.put("Г", "G");
        letters.put("Д", "D");
        letters.put("Е", "E");
        letters.put("Ё", "E");
        letters.put("Ж", "Zh");
        letters.put("З", "Z");
        letters.put("И", "I");
        letters.put("Й", "I");
        letters.put("К", "K");
        letters.put("Л", "L");
        letters.put("М", "M");
        letters.put("Н", "N");
        letters.put("О", "O");
        letters.put("П", "P");
        letters.put("Р", "R");
        letters.put("С", "S");
        letters.put("Т", "T");
        letters.put("У", "U");
        letters.put("Ф", "F");
        letters.put("Х", "Kh");
        letters.put("Ц", "C");
        letters.put("Ч", "Ch");
        letters.put("Ш", "Sh");
        letters.put("Щ", "Sch");
        letters.put("Ъ", "'");
        letters.put("Ы", "Y");
        letters.put("Ъ", "'");
        letters.put("Э", "E");
        letters.put("Ю", "Yu");
        letters.put("Я", "Ya");
        letters.put("а", "a");
        letters.put("б", "b");
        letters.put("в", "v");
        letters.put("г", "g");
        letters.put("д", "d");
        letters.put("е", "e");
        letters.put("ё", "e");
        letters.put("ж", "zh");
        letters.put("з", "z");
        letters.put("и", "i");
        letters.put("й", "i");
        letters.put("к", "k");
        letters.put("л", "l");
        letters.put("м", "m");
        letters.put("н", "n");
        letters.put("о", "o");
        letters.put("п", "p");
        letters.put("р", "r");
        letters.put("с", "s");
        letters.put("т", "t");
        letters.put("у", "u");
        letters.put("ф", "f");
        letters.put("х", "h");
        letters.put("ц", "c");
        letters.put("ч", "ch");
        letters.put("ш", "sh");
        letters.put("щ", "sch");
        letters.put("ъ", "'");
        letters.put("ы", "y");
        letters.put("ъ", "'");
        letters.put("э", "e");
        letters.put("ю", "yu");
        letters.put("я", "ya");
    }



    public static String toTranslit(String Inputtext) {
        String ext = FilenameUtils.getExtension(Inputtext);
        String text = FilenameUtils.removeExtension(Inputtext);
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i<text.length(); i++) {
            String l = text.substring(i, i+1);
            if (letters.containsKey(l)) {
                sb.append(letters.get(l));
            }
            else if (l.equals(".") || l.equals(" ")){
                sb.append("_");
            }else{
                sb.append(l);
            }
        }
        return sb.toString()+"."+ext;
    }

}
