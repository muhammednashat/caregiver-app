package mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.useCase

import android.util.Log
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryContentRepo

class GetLibraryContentUseCase(private val libraryRepository: LibraryContentRepo) {


    suspend operator fun invoke() =

      libraryRepository.getLibraryContent()
}

