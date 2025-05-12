import SwiftUI
import SharedDomain

struct ContentView: View {
    @StateObject private var viewModel = TodoViewModelWrapper()
    @State private var newTodoTitle = ""

    var body: some View {
        TodoListScreen()
    }
}

extension TodoUIState {
    var isLoading: Bool {
        return self is TodoUIStateLoading
    }

    var isSuccess: Bool {
        return self is TodoUIStateSuccess
    }

    var todos: [Todo] {
        if let success = self as? TodoUIStateSuccess {
            return success.todos
        }
        return []
    }

    var errorMessage: String? {
        if let error = self as? TodoUIStateError {
            return error.message
        }
        return nil
    }
}

extension Todo: Identifiable {}
