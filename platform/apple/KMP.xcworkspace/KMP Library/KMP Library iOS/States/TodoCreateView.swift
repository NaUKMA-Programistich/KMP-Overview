import SwiftUI
import SharedDomain

struct TodoCreateView: View {
    let onCreate: (String, Bool) -> Void
    let onDismiss: () -> Void

    @State private var title: String = ""
    @State private var completed: Bool = false

    var body: some View {
        TodoDialogView(title: "Create Todo") {
            VStack(spacing: 16) {
                TextField("Title", text: $title)
                    .textFieldStyle(RoundedBorderTextFieldStyle())

                HStack {
                    Text("Completed")
                    Spacer()
                    Toggle("", isOn: $completed)
                        .labelsHidden()
                }

                HStack {
                    Spacer()

                    Button("Cancel") {
                        onDismiss()
                    }

                    Button("Create") {
                        onCreate(title, completed)
                    }
                    .disabled(title.isEmpty)
                    .buttonStyle(.borderedProminent)
                }
                .padding(.top, 8)

                Spacer()
            }
        }
    }
}
